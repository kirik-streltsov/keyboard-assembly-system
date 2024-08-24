package com.kirikstreltsov.customerbot.formatters

import com.kirikstreltsov.customerbot.dto.Order
import org.springframework.stereotype.Component

@Component
class OrderCompletedFormatter : Formatter<Order> {
    override fun format(dto: Order): String {
        val builder = StringBuilder()

        with(builder) {
            builder.append("Заказ с номером ${dto.id} готов.\n")
            builder.append("Никнейм исполнителя: ${dto.employee!!.username}\n")
        }

        return builder.toString()
    }
}