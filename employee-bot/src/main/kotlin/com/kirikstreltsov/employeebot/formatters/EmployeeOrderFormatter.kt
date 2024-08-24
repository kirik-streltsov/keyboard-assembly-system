package com.kirikstreltsov.employeebot.formatters

import com.kirikstreltsov.employeebot.dto.Order
import org.springframework.stereotype.Component

@Component
class EmployeeOrderFormatter : Formatter<Order> {
    override fun format(dto: Order): String {
        val stringBuilder = StringBuilder()

        with(stringBuilder) {
            append("Id заказа: ${dto.id}\n")
            append("Username заказчика: ${dto.customer?.username}\n")
            append("Клавиатура состоит из:\n")
            append("Кейса: ${dto.keyboard?.case?.name}\n")
            append("Свитчей: ${dto.keyboard?.switch?.name}\n")
            append("Кейкапов: ${dto.keyboard?.keycap?.name}\n")
        }

        return stringBuilder.toString()
    }
}