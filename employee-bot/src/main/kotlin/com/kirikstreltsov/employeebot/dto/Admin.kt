package com.kirikstreltsov.employeebot.dto

data class Admin(val telegramId: Long, val username: String) {
    constructor(): this(0, "")
}