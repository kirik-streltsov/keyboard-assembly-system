package com.kirikstreltsov.employeebot.dto

data class Customer(val telegramId: Long, val username: String) {
    constructor() : this(0L, "")
}
