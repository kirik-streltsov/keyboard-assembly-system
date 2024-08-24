package com.kirikstreltsov.orders

import com.kirikstreltsov.keyboards.CreateKeyboardRequest

data class CreateOrderRequest(val keyboard: CreateKeyboardRequest, val customerId: Long)
