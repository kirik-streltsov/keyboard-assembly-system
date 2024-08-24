package com.kirikstreltsov.adminbot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.dto.Customer
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody

object CustomerRequestFactory {
    private const val URL = "http://localhost:8091/api/v1/customers"
    private val objectMapper = ObjectMapper()

    fun getCustomerByTelegramIdRequest(telegramId: Long) = RequestFactory.createGetRequest("$URL/$telegramId")
    fun createCustomerRequest(telegramId: Long, username: String) = RequestFactory.createPostRequest(
        URL,
        RequestBody.create(
            MediaType.parse("application/json"),
            objectMapper.writeValueAsString(Customer(telegramId, "@${username}"))
        )
    )
}