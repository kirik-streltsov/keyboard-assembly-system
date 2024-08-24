package com.kirikstreltsov.adminbot.dto

data class CreateKeyboardDto(
    val caseId: Long,
    val switchId: Long,
    val keycapId: Long
)