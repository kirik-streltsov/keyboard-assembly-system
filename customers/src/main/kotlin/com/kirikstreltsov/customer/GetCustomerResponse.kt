package com.kirikstreltsov.customer

import com.kirikstreltsov.entities.Customer

data class GetCustomerResponse(val telegramId: Long, val username: String) {
    companion object {
        fun fromCustomer(customer: Customer): GetCustomerResponse
        = GetCustomerResponse(customer.telegramId, customer.username)
    }
}