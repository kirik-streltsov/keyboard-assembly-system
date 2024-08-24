package com.kirikstreltsov.customerbot.formatters

import com.kirikstreltsov.customerbot.dto.Order
import org.springframework.stereotype.Component

@Component
class KeyboardOrderFormatter : Formatter<Order> {
    override fun format(dto: Order): String {
        val stringBuilder = StringBuilder()

        with(stringBuilder) {
            append("Username заказчика: ${dto.customer?.username}\n")
            append("Клавиатура состоит из:\n")
            append("Кейса: ${dto.keyboard?.case?.name}\n")
            append("Свитчей: ${dto.keyboard?.switch?.name}\n")
            append("Кейкапов: ${dto.keyboard?.keycap?.name}\n")

            val readinessState = when (dto.readinessState) {
                "Created" -> "Заказ создан, и совсем скоро над ним начнет работать сотрудник"
                "In Progress" -> "Сотрудник начал выполнять заказ"
                "Completed" -> "Заказ готов"
                else -> "Заказ отклонен"
            }

            append("Готовность: $readinessState\n")
        }
        return stringBuilder.toString()
    }
}