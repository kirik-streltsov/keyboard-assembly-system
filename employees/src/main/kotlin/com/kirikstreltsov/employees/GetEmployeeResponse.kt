package com.kirikstreltsov.employees

data class GetEmployeeResponse(val telegramId: Long, val username: String, val approved: Boolean)