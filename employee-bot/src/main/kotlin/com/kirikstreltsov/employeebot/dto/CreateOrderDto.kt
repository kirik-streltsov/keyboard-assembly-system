package com.kirikstreltsov.employeebot.dto

import com.kirikstreltsov.employeebot.dto.CreateKeyboardDto

data class CreateOrderDto(val keyboard: CreateKeyboardDto, val customerId: Long)