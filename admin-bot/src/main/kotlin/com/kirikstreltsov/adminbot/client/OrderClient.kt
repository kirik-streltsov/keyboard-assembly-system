package com.kirikstreltsov.adminbot.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.dto.CreateOrderDto
import com.kirikstreltsov.adminbot.dto.Order
import com.kirikstreltsov.adminbot.dto.OrderPatchDto
import com.kirikstreltsov.adminbot.factory.OrderRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class OrderClient(private val webClient: OkHttpClient, private val objectMapper: ObjectMapper) {
    fun getAllOrders(): List<Order> {
        val request = OrderRequestFactory.getAllOrders()
        val response = webClient.newCall(request).execute()

        val orders = objectMapper.readValue(
            response.body().bytes(),
            object : TypeReference<List<Order>>() {}
        )

        return orders
    }

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

    fun setEmployeeToOrder(orderId: Long, employeeId: Long): Order {
        val dto = OrderPatchDto(employeeId)
        val request = OrderRequestFactory.setEmployeeToOrder(orderId, dto)
        val response = webClient.newCall(request).execute()

        val order = objectMapper.readValue(
            response.body().bytes(),
            Order::class.java
        )

        return order
    }
}