package com.kirikstreltsov.customerbot.dto

data class CreateOrderDto(val keyboard: CreateKeyboardDto, val customerId: Long)