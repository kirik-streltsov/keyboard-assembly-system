package com.kirikstreltsov.adminbot.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.dto.Admin
import com.kirikstreltsov.adminbot.dto.CreateAdminDto
import com.kirikstreltsov.adminbot.factory.AdminRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class AdminClient(private val webClient: OkHttpClient, private val objectMapper: ObjectMapper) {
    fun createAdmin(dto: CreateAdminDto, createdBy: Long): Admin {
        val request = AdminRequestFactory.createAdmin(dto, createdBy)
        val response = webClient.newCall(request).execute()

        val admin = objectMapper.readValue(response.body().bytes(), Admin::class.java)
        return admin
    }

    fun fireAdmin(telegramId: Long, firedBy: Long): Admin {
        val request = AdminRequestFactory.fireAdmin(telegramId, firedBy)
        val response = webClient.newCall(request).execute()

        val admin = objectMapper.readValue(response.body().bytes(), Admin::class.java)
        return admin
    }

    fun updateAdminUsernameByTelegramId(telegramId: Long, newUsername: String): Int {
        val request = AdminRequestFactory.updateAdminUsernameByTelegramId(telegramId, newUsername)
        val response = webClient.newCall(request).execute()

        return response.code()
    }
}