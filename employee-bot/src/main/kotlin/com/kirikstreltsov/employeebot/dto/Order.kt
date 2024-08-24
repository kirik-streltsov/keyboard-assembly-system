package com.kirikstreltsov.employeebot.dto

import com.kirikstreltsov.employeebot.dto.Customer
import com.kirikstreltsov.employeebot.dto.Employee
import com.kirikstreltsov.employeebot.dto.Keyboard

data class Order(
    val id: Long,
    val keyboard: Keyboard?,
    val customer: Customer?,
    val employee: Employee?,
    val readinessState: String
) {
    constructor() : this(0L, null, null, null, "")
}