package com.kirikstreltsov.employees

import com.kirikstreltsov.entities.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByTelegramId(telegramId: Long): Employee?
}