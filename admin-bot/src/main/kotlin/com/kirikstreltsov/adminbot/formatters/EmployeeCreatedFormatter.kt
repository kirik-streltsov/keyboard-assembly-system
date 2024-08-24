package com.kirikstreltsov.adminbot.formatters

import com.kirikstreltsov.adminbot.dto.Employee
import org.springframework.stereotype.Component

@Component
class EmployeeCreatedFormatter : Formatter<Employee> {
    override fun format(dto: Employee): String = "${dto.username} хочет стать сотрудником. Принимаем?"
}