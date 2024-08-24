package com.kirikstreltsov.customer

import com.kirikstreltsov.entities.Customer
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(private val customerService: CustomerService) {
    @GetMapping
    fun findAll(): List<GetCustomerResponse> = customerService.findAll()
        .map { GetCustomerResponse(it.telegramId, it.username) }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): GetCustomerResponse? {
        val customer = customerService.findById(id) ?: return null
        return GetCustomerResponse(customer.telegramId, customer.username)
    }

    @PostMapping
    fun create(@RequestBody dto: CreateCustomerRequest): GetCustomerResponse {
        val customer = Customer(dto.telegramId, dto.username)
        customerService.save(customer)
        return GetCustomerResponse(customer.telegramId, customer.username)
    }

    @PatchMapping("/{id}/username")
    fun changeUsername(@PathVariable id: Long, dto: UpdateCustomerUsernameRequest): GetCustomerResponse {
        val customer = customerService.changeUsernameById(id, dto.username)
        return GetCustomerResponse(customer.telegramId, customer.username)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = customerService.delete(id)
}