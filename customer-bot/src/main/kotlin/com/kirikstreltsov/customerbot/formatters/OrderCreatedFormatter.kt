package com.kirikstreltsov.customerbot.formatters

import com.kirikstreltsov.customerbot.dto.Order
import org.springframework.stereotype.Component

/**
 * Used for customer message formatting
 */
@Component
class OrderCreatedFormatter : Formatter<Order> {
    override fun format(dto: Order): String {
        return "Заказ успешно оформлен.\nНомер заказа: ${dto.id}"
    }
}