package com.kirikstreltsov.adminbot.formatters

interface Formatter<DTO> {
    fun format(dto: DTO): String
}