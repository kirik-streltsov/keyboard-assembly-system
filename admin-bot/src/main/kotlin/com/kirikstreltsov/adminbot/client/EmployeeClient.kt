package com.kirikstreltsov.adminbot.client

import com.kirikstreltsov.adminbot.factory.EmployeeRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class EmployeeClient(private val webClient: OkHttpClient) {
    fun approveEmployee(telegramId: Long): Int {
        val request = EmployeeRequestFactory.approveEmployeeByTelegramIdRequest(telegramId)
        val response = webClient.newCall(request).execute()

        return response.code()
    }

    fun declineEmployee(telegramId: Long): Int {
        val request = EmployeeRequestFactory.declineEmployeeByTelegramIdRequest(telegramId)
        val response = webClient.newCall(request).execute()

        return response.code()
    }
}