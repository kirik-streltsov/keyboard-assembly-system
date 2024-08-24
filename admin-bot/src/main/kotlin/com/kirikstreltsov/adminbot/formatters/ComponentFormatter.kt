package com.kirikstreltsov.adminbot.formatters

import com.kirikstreltsov.adminbot.dto.GetKeyboardComponentDto
import org.springframework.stereotype.Component

@Component
class ComponentFormatter : Formatter<GetKeyboardComponentDto> {
    override fun format(dto: GetKeyboardComponentDto): String {
        val stringBuilder = StringBuilder()

        with(stringBuilder) {
            append("Id: ${dto.id}\n")
            append("Название: ${dto.name}\n")
            append("Цена: ${dto.price}\n")
        }

        return stringBuilder.toString()
    }
}