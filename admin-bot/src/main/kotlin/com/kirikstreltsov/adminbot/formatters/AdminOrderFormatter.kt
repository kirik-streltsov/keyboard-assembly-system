package com.kirikstreltsov.adminbot.formatters

import com.kirikstreltsov.adminbot.dto.Order
import org.springframework.stereotype.Component

@Component
class AdminOrderFormatter : Formatter<Order> {
    override fun format(dto: Order): String {
        val stringBuilder = StringBuilder()

        with(stringBuilder) {
            append("Id заказа: ${dto.id}\n")
            append("Username заказчика: ${dto.customer?.username}\n")
            append("Кейс: ${dto.keyboard?.case?.name}\n")
            append("Свитчи: ${dto.keyboard?.switch?.name}\n")
            append("Кейкапы: ${dto.keyboard?.keycap?.name}\n")

            val employeeText = dto.employee?.username ?: "Никто"
            append("Над заказом работает: ${employeeText}\n")

            val readinessText = when (dto.readinessState) {
                "Created" -> "Заказ создан, и совсем скоро над ним начнет работать сотрудник"
                "In Progress" -> "Сотрудник начал выполнять заказ"
                "Completed" -> "Заказ готов"
                else -> "Заказ отклонен"
            }
            append("Готовность: ${readinessText}\n")
        }

        return stringBuilder.toString()
    }
}