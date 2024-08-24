package com.kirikstreltsov.orders

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.kirikstreltsov.entities.Order

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findOrderByEmployeeTelegramId(employeeTelegramId: Long): List<Order>
}