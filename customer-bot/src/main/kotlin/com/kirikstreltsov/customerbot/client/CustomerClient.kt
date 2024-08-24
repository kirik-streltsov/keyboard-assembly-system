package com.kirikstreltsov.customerbot.client

import com.kirikstreltsov.customerbot.factory.CustomerRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class CustomerClient(private val webClient: OkHttpClient) {
    fun createCustomer(telegramId: Long, username: String): Int {
        val request = CustomerRequestFactory.createCustomerRequest(telegramId, username)
        val response = webClient.newCall(request).execute()

        return response.code()
    }

    fun updateCustomerUsernameByTelegramId(telegramId: Long, newUsername: String): Int {
        val request = CustomerRequestFactory.updateCustomerUsernameByTelegramIdRequest(telegramId, newUsername)
        val response = webClient.newCall(request).execute()

        return response.code()
    }
}