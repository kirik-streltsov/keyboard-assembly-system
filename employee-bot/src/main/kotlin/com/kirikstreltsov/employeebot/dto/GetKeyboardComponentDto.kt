package com.kirikstreltsov.employeebot.dto

data class GetKeyboardComponentDto(val id: Long, val name: String, val price: Double) {
    constructor() : this(0, "", 0.0)
}