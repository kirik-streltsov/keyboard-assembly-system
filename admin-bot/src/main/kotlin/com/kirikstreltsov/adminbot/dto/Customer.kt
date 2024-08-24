package com.kirikstreltsov.adminbot.dto

data class Customer(val telegramId: Long, val username: String) {
    constructor() : this(0L, "")
}
