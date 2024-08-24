package com.kirikstreltsov.customerbot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.customerbot.dto.CreateOrderDto
import com.kirikstreltsov.customerbot.dto.OrderPatchDto
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody

object OrderRequestFactory {
    private const val URL = "http://localhost:8092/api/v1/orders"
    private val objectMapper = ObjectMapper()

    fun getOrderById(orderId: Long) = RequestFactory.createGetRequest("$URL/$orderId")
    fun getAllOrders() = RequestFactory.createGetRequest(URL)
    fun createOrder(dto: CreateOrderDto) = RequestFactory.createPostRequest(
        URL,
        RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(dto))
    )
    fun setEmployeeToOrder(orderId: Long, dto: OrderPatchDto) = RequestFactory.createPatchRequest(
        "$URL/$orderId",
        RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(dto))
    )
}