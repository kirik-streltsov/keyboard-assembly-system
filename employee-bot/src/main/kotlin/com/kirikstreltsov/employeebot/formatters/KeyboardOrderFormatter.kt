package com.kirikstreltsov.employeebot.formatters

import com.kirikstreltsov.employeebot.dto.Order
import com.kirikstreltsov.employeebot.formatters.Formatter
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

            val employeeName = dto.employee?.username ?: "Никто"
            append("Сотрудник: ${employeeName}\n")
        }
        return stringBuilder.toString()
    }
}