package com.kirikstreltsov.customer

import com.kirikstreltsov.entities.Customer
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CustomerService(private val customerRepository: CustomerRepository) {
    fun findAll(): List<Customer> = customerRepository.findAll()
    fun findById(id: Long): Customer? = customerRepository.findById(id).orElse(null)
    fun save(customer: Customer): Customer {
        if (customerRepository.existsByTelegramId(customer.telegramId))
            throw CustomerExistsException()

        return customerRepository.save(customer)
    }

    fun delete(id: Long): Customer {
        val customer = customerRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found") }
        customerRepository.deleteById(id)
        return customer
    }

    fun changeUsernameById(telegramId: Long, username: String): Customer {
        val customer = customerRepository.findById(telegramId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found") }
        customer.username = username
        return customerRepository.save(customer)
    }
}