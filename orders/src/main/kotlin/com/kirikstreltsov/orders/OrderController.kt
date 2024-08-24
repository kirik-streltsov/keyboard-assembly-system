package com.kirikstreltsov.orders

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.componentsclient.ComponentsClient
import com.kirikstreltsov.customersclient.CustomersClient
import com.kirikstreltsov.employeesclient.EmployeesClient
import com.kirikstreltsov.entities.*
import com.kirikstreltsov.messagingclient.MessagingClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    val service: OrderService,
    val customersClient: CustomersClient,
    val componentsClient: ComponentsClient,
    val employeesClient: EmployeesClient,
    private val messagingClient: MessagingClient,
    private val objectMapper: ObjectMapper
) {
    @GetMapping
    fun getAll(): List<GetOrderResponse> = service.findAll().map { GetOrderResponse.fromOrder(it) }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): GetOrderResponse? {
        val order = service.findById(id) ?: return null
        return GetOrderResponse.fromOrder(order)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        params = ["employeeId"]
    )
    fun getOrderWithEmployeeId(@RequestParam(name = "employeeId") employeeId: Long): List<GetOrderResponse> =
        service.findWithEmployeeId(employeeId).map { GetOrderResponse.fromOrder(it) }

    @GetMapping("/vacant")
    fun getVacantOrders(): List<GetOrderResponse> = service.findVacantOrders().map { GetOrderResponse.fromOrder(it) }

    @PostMapping
    fun createOrder(@RequestBody dto: CreateOrderRequest): GetOrderResponse {
        // customer
        val customerOrders = service.findOrdersByCustomerId(dto.customerId)
        val customerDto = customersClient.getCustomerById(dto.customerId)!!
        val customer = Customer(customerDto.telegramId, customerDto.username, customerOrders)

        // case
        val caseDto = componentsClient.getCaseById(dto.keyboard.caseId)!!
        val case = Case(caseDto.id, caseDto.name, caseDto.price)

        // switch
        val switchDto = componentsClient.getSwitchById(dto.keyboard.switchId)!!
        val switch = Switch(switchDto.id, switchDto.name, switchDto.price)

        // keycap
        val keycapDto = componentsClient.getKeycapById(dto.keyboard.keycapId)!!
        val keycap = Keycap(keycapDto.id, keycapDto.name, keycapDto.price)

        val keyboard = Keyboard(case, switch, keycap)

        val order = Order(keyboard, customer)
        service.save(order)

        val getOrderResponse = GetOrderResponse.fromOrder(order)
        messagingClient.sendMessage("order-created", objectMapper.writeValueAsString(getOrderResponse))

        return getOrderResponse
    }

    @PatchMapping("/{id}")
    fun setEmployeeByOrderId(
        @PathVariable("id") id: Long,
        @RequestBody dto: SetEmployeePatchRequest
    ): GetOrderResponse {
        val employeeResponse = employeesClient.getEmployeeById(dto.employeeId)!!
        val employee = Employee(employeeResponse.telegramId, employeeResponse.username, employeeResponse.approved)
        val order = service.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        service.setEmployeeById(id, employee)
        service.setReadinessStateById(id, ReadinessState.IN_PROGRESS.name)
        return GetOrderResponse.fromOrder(order)
    }

    @PatchMapping("/{id}/readiness")
    fun setReadinessStateByOrderId(
        @PathVariable("id") id: Long,
        @RequestBody dto: SetReadinessStateRequest
    ): GetOrderResponse {
        val order = service.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        service.setReadinessStateById(id, dto.readinessState)

        messagingClient.sendMessage(
            "order-${dto.readinessState.lowercase()}",
            objectMapper.writeValueAsString(GetOrderResponse.fromOrder(order))
        )

        return GetOrderResponse.fromOrder(order)
    }
}