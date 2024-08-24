package com.kirikstreltsov.employees

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.entities.Employee
import com.kirikstreltsov.messagingclient.MessagingClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/employees")
class EmployeeController(
    private val service: EmployeeService,
    private val messagingClient: MessagingClient,
    private val objectMapper: ObjectMapper
) {
    @GetMapping("/{telegramId}")
    fun getEmployeeByTelegramId(@PathVariable("telegramId") telegramId: Long): GetEmployeeResponse? {
        val employee = service.findByTelegramId(telegramId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return GetEmployeeResponse(employee.telegramId, employee.username, employee.approved)
    }

    @GetMapping
    fun getEmployees(): List<GetEmployeeResponse> = service.findAll()
        .map { GetEmployeeResponse(it.telegramId, it.username, it.approved) }

    @PostMapping
    fun createEmployee(@RequestBody dto: CreateEmployeeRequest): GetEmployeeResponse {
        val employee = Employee(dto.telegramId, dto.username)
        service.save(employee)
        val response = GetEmployeeResponse(employee.telegramId, employee.username, employee.approved)
        messagingClient.sendMessage("employee-created", objectMapper.writeValueAsString(response))
        return response
    }

    @PatchMapping("/approve/{id}")
    fun approveById(@PathVariable id: Long): GetEmployeeResponse = service.approveById(id)
        .let {

            val response = GetEmployeeResponse(it.telegramId, it.username, it.approved)
            messagingClient.sendMessage("employee-approve", it.telegramId.toString())
            response
        }

    @PatchMapping("/{id}/username")
    fun changeUsername(@PathVariable id: Long, @RequestBody dto: ChangeEmployeeUsernameRequest): GetEmployeeResponse {
        val employee = service.changeUsernameById(id, dto.username)
        return GetEmployeeResponse(employee.telegramId, employee.username, employee.approved)
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Long): GetEmployeeResponse {
        val employee = service.findByTelegramId(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")
        service.delete(id)
        return GetEmployeeResponse(employee.telegramId, employee.username, employee.approved)
    }

    @DeleteMapping("/decline/{id}")
    fun declineEmployee(@PathVariable id: Long): GetEmployeeResponse {
        val employee = service.findByTelegramId(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")
        service.delete(id)
        messagingClient.sendMessage("employee-declined", employee.telegramId.toString())
        return GetEmployeeResponse(employee.telegramId, employee.username, employee.approved)
    }
}