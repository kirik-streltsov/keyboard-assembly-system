package com.kirikstreltsov.adminbot.formatters

import com.kirikstreltsov.adminbot.dto.Order
import org.springframework.stereotype.Component

/**
 * Used for admin channel post formatting
 */
@Component
class OrderCreatedChannelMessageFormatter : Formatter<Order> {
    override fun format(dto: Order): String {
        val stringBuilder = StringBuilder()

        with(stringBuilder) {
            append("Создан заказ:\n")
            append("Номер заказа: ${dto.id}\n")
            append("Заказчик: ${dto.customer?.username}\n")
            append("Кейс: ${dto.keyboard?.case?.name}\n")
            append("Свитчи: ${dto.keyboard?.switch?.name}\n")
            append("Кейкапы: ${dto.keyboard?.keycap?.name}\n")
        }

        return stringBuilder.toString()
    }
}