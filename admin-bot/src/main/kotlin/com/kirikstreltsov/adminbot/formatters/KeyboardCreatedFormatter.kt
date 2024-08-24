package com.kirikstreltsov.adminbot.formatters

import com.kirikstreltsov.adminbot.dto.Keyboard
import org.springframework.stereotype.Component

@Component
class KeyboardCreatedFormatter : Formatter<Keyboard> {
    override fun format(dto: Keyboard): String {
        val stringBuilder = StringBuilder()

        with(stringBuilder) {
            append("Вы собрали клавиатуру из:\n")
            append("Кейса: ${dto.case!!.name}\n")
            append("Свитчей: ${dto.switch!!.name}\n")
            append("Кейкапов: ${dto.keycap!!.name}\n")
            append("Оформляем заказ?")
        }

        return stringBuilder.toString()
    }
}