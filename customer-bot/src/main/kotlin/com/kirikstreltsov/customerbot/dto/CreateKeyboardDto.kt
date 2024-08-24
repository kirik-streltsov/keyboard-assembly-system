package com.kirikstreltsov.customerbot.dto

data class CreateKeyboardDto(
    val caseId: Long,
    val switchId: Long,
    val keycapId: Long
)