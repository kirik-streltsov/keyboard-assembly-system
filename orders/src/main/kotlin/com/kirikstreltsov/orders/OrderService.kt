package com.kirikstreltsov.orders

import com.kirikstreltsov.entities.Employee
import com.kirikstreltsov.entities.Order
import com.kirikstreltsov.entities.ReadinessState
import org.springframework.stereotype.Service

@Service
class OrderService(private val repository: OrderRepository) {
    fun save(order: Order): Order = repository.save(order)
    fun findAll(): List<Order> = repository.findAll()
    fun findById(id: Long): Order? = repository.findById(id).orElseThrow()
    fun findWithEmployeeId(employeeId: Long): List<Order> = repository.findOrderByEmployeeTelegramId(employeeId)
    fun findVacantOrders(): List<Order> = repository.findAll().filter {
        it.employee == null && it.readinessState != ReadinessState.COMPLETED
    }

    fun findOrdersByCustomerId(customerTelegramId: Long): List<Order> = repository.findAll()
        .filter { it.customer.telegramId == customerTelegramId }

    fun setEmployeeById(id: Long, employee: Employee): Order {
        val order = repository.findById(id).orElseThrow()
        order.employee = employee
        repository.save(order)
        return order
    }

    fun setReadinessStateById(id: Long, readinessState: String): Order {
        val order = repository.findById(id).orElseThrow()
        order.readinessState = ReadinessState.valueOf(readinessState)
        repository.save(order)
        return order
    }
}