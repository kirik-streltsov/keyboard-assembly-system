package com.kirikstreltsov.employeebot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.employeebot.dto.CreateEmployeeDto
import com.kirikstreltsov.employeebot.dto.UpdateEmployeeUsernameDto
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody

object EmployeeRequestFactory {
    private const val URL = "http://localhost:8093/api/v1/employees"
    private val objectMapper = ObjectMapper()
    fun getEmployeeByTelegramIdRequest(telegramId: Long) = RequestFactory.createGetRequest("$URL/$telegramId")
    fun createEmployeeRequest(dto: CreateEmployeeDto) = RequestFactory.createPostRequest(
        URL, RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            objectMapper.writeValueAsString(dto)
        )
    )

    fun fireEmployeeByTelegramIdRequest(telegramId: Long) = RequestFactory.createDeleteRequest("$URL/$telegramId")
    fun updateEmployeeUsernameByTelegramId(telegramId: Long, newUsername: String) = RequestFactory.createPatchRequest(
        "$URL/$telegramId/username", RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            objectMapper.writeValueAsString(UpdateEmployeeUsernameDto(newUsername))
        )
    )
}