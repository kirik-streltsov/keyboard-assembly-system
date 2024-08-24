package com.kirikstreltsov.adminbot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.dto.CreateAdminDto
import com.kirikstreltsov.adminbot.dto.UpdateAdminUsernameDto
import com.squareup.okhttp.Headers
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody

object AdminRequestFactory {
    private const val URL = "http://localhost:8094/api/v1/admin"
    private val objectMapper = ObjectMapper()

    fun getAdminByTelegramId(telegramId: Long) = RequestFactory
        .createGetRequest("$URL/$telegramId", Headers.of(mapOf("Authorization" to "$telegramId")))

    /**
     * @param createdBy - telegram id of admin who performs the addition
     */
    fun createAdmin(dto: CreateAdminDto, createdBy: Long) = RequestFactory.createPostRequest(
        URL, RequestBody.create(
            MediaType.parse("application/json"), objectMapper.writeValueAsString(dto)
        ), Headers.of(mapOf("Authorization" to "$createdBy"))
    )

    fun fireAdmin(telegramId: Long, firedBy: Long) = RequestFactory.createDeleteRequest(
        "$URL/$telegramId",
        Headers.of(mapOf("Authorization" to "$firedBy"))
    )

    fun updateAdminUsernameByTelegramId(telegramId: Long, newUsername: String) = RequestFactory.createPatchRequest(
        "$URL/$telegramId/username", RequestBody.create(
            MediaType.parse("application/json"),
            objectMapper.writeValueAsString(UpdateAdminUsernameDto(newUsername))
        ), Headers.of(mapOf("Authorization" to telegramId.toString()))
    )
}