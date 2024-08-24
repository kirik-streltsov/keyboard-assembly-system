package com.kirikstreltsov.customerbot.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.customerbot.dto.CreateEmployeeDto
import com.kirikstreltsov.customerbot.dto.Employee
import com.kirikstreltsov.customerbot.factory.EmployeeRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class EmployeeClient(private val webClient: OkHttpClient, private val objectMapper: ObjectMapper) {
    fun getEmployeeByTelegramId(telegramId: Long): Employee? {
        val request = EmployeeRequestFactory.getEmployeeByTelegramIdRequest(telegramId)
        val response = webClient.newCall(request).execute()

        if (!response.isSuccessful)
            return null

        val employee = objectMapper.readValue(response.body().bytes(), Employee::class.java)
        return employee
    }

    fun createEmployee(telegramId: Long, username: String): Int {
        val request = EmployeeRequestFactory.createEmployeeRequest(CreateEmployeeDto(telegramId, username))
        val response = webClient.newCall(request).execute()

        return response.code()
    }
}