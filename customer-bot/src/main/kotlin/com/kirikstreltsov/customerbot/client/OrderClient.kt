package com.kirikstreltsov.customerbot.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.customerbot.dto.CreateOrderDto
import com.kirikstreltsov.customerbot.dto.Order
import com.kirikstreltsov.customerbot.dto.OrderPatchDto
import com.kirikstreltsov.customerbot.factory.OrderRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class OrderClient(private val webClient: OkHttpClient, private val objectMapper: ObjectMapper) {
    fun getOrderById(orderId: Long): Order {
        val request = OrderRequestFactory.getOrderById(orderId)
        val response = webClient.newCall(request).execute()

        val order = objectMapper.readValue(
            response.body().bytes(),
            Order::class.java
        )

        return order
    }

    fun createOrder(dto: CreateOrderDto): Order {
        val request = OrderRequestFactory.createOrder(dto)
        val response = webClient.newCall(request).execute()

        val order = objectMapper.readValue(
            response.body().bytes(),
            Order::class.java
        )

        return order
    }
}