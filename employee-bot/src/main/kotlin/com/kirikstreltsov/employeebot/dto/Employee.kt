package com.kirikstreltsov.employeebot.dto

data class Employee(val telegramId: Long, val username: String, val approved: Boolean) {
    constructor(): this(0L, "", false)
}
