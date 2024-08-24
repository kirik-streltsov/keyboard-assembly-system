package com.kirikstreltsov.employeebot.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.employeebot.dto.Employee
import com.kirikstreltsov.employeebot.factory.EmployeeRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class EmployeeClient(private val webClient: OkHttpClient, private val objectMapper: ObjectMapper) {
    fun getEmployeeByTelegramId(telegramId: Long): Employee? {
        val request = EmployeeRequestFactory.getEmployeeByTelegramIdRequest(telegramId)
        val response = webClient.newCall(request).execute()

        if (!response.isSuccessful)
            return null

        val body = response.body().string()
        return objectMapper.readValue(body, Employee::class.java)
    }

    fun fireEmployeeByTelegramId(telegramId: Long): Int {
        val request = EmployeeRequestFactory.fireEmployeeByTelegramIdRequest(telegramId)
        val response = webClient.newCall(request).execute()

        return response.code()
    }

    fun updateEmployeeUsernameByTelegramId(telegramId: Long, newUsername: String): Int {
        val request = EmployeeRequestFactory.updateEmployeeUsernameByTelegramId(telegramId, newUsername)
        val response = webClient.newCall(request).execute()

        return response.code()
    }
}