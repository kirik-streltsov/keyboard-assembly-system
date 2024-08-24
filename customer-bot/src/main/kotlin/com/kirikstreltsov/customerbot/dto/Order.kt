package com.kirikstreltsov.customerbot.dto

import com.kirikstreltsov.customerbot.dto.Customer
import com.kirikstreltsov.customerbot.dto.Employee
import com.kirikstreltsov.customerbot.dto.Keyboard

data class Order(
    val id: Long,
    val keyboard: Keyboard?,
    val customer: Customer?,
    val employee: Employee?,
    val readinessState: String
) {
    constructor() : this(0L, null, null, null, "")
}