package com.kirikstreltsov.customerbot.formatters

interface Formatter<DTO> {
    fun format(dto: DTO): String
}