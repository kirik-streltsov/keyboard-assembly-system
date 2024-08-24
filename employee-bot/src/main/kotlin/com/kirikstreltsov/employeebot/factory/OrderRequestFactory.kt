package com.kirikstreltsov.employeebot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.employeebot.dto.CreateOrderDto
import com.kirikstreltsov.employeebot.dto.OrderPatchDto
import com.kirikstreltsov.employeebot.dto.ReadinessState
import com.kirikstreltsov.employeebot.dto.SetReadinessStateDto
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

    fun getAllVacantOrders() = RequestFactory.createGetRequest("$URL/vacant")
    fun getOrdersWithEmployeeId(employeeId: Long) = RequestFactory.createGetRequest("$URL?employeeId=$employeeId")
    fun setOrderReadinessStateById(orderId: Long, state: ReadinessState) = RequestFactory.createPatchRequest(
        "$URL/$orderId/readiness",
        RequestBody.create(
            MediaType.parse("application/json"),
            objectMapper.writeValueAsString(SetReadinessStateDto(state.name))
        )
    )
}