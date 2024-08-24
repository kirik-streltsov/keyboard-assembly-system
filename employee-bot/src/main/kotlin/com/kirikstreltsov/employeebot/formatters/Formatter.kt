package com.kirikstreltsov.employeebot.formatters

interface Formatter<DTO> {
    fun format(dto: DTO): String
}