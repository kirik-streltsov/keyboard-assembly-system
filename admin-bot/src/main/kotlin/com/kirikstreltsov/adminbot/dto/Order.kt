package com.kirikstreltsov.adminbot.dto

data class Order(
    val id: Long,
    val keyboard: Keyboard?,
    val customer: Customer?,
    val employee: Employee?,
    val readinessState: String
) {
    constructor() : this(0L, null, null, null, "")
}