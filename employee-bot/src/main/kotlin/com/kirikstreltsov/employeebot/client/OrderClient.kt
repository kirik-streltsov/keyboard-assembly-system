package com.kirikstreltsov.employeebot.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.employeebot.dto.CreateOrderDto
import com.kirikstreltsov.employeebot.dto.Order
import com.kirikstreltsov.employeebot.dto.OrderPatchDto
import com.kirikstreltsov.employeebot.dto.ReadinessState
import com.kirikstreltsov.employeebot.factory.OrderRequestFactory
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

    fun getOrderById(orderId: Long): Order? {
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

    fun getAllVacantOrders(): List<Order> {
        val request = OrderRequestFactory.getAllVacantOrders()
        val response = webClient.newCall(request).execute()

        val orders = objectMapper.readValue(
            response.body().bytes(),
            object : TypeReference<List<Order>>() {}
        )
        return orders
    }

    fun getOrdersWithEmployeeId(employeeId: Long): List<Order> {
        val request = OrderRequestFactory.getOrdersWithEmployeeId(employeeId)
        val response = webClient.newCall(request).execute()

        val orders = objectMapper.readValue(
            response.body().bytes(),
            object : TypeReference<List<Order>>() {}
        )
        return orders
    }

    fun setOrderReadinessStateById(orderId: Long, readinessState: ReadinessState): Int {
        val request = OrderRequestFactory.setOrderReadinessStateById(orderId, readinessState)
        val response = webClient.newCall(request).execute()

        return response.code()
    }
}