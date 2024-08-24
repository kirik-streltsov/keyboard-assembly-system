package com.kirikstreltsov.customer

import com.kirikstreltsov.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    fun existsByTelegramId(telegramId: Long): Boolean
}