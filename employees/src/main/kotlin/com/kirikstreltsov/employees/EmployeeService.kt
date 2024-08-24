package com.kirikstreltsov.employees

import com.kirikstreltsov.entities.Employee
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class EmployeeService(val repository: EmployeeRepository) {
    fun findAll(): List<Employee> = repository.findAll()
    fun save(employee: Employee): Employee = repository.save(employee)
    fun findByTelegramId(telegramId: Long) = repository.findByTelegramId(telegramId)
    fun delete(id: Long) : Employee {
        val employee = repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found") }
        repository.delete(employee)
        return employee
    }
    fun approveById(id: Long) : Employee {
        val employee = repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found") }
        employee.approved = true
        repository.save(employee)
        return employee
    }

    fun changeUsernameById(id: Long, username: String) : Employee {
        val employee = repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found") }
        employee.username = username
        repository.save(employee)
        return employee
    }
}