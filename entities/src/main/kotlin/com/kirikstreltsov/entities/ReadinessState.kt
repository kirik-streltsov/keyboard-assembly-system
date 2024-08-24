package com.kirikstreltsov.entities

enum class ReadinessState(val value: String) {
    CREATED("Created"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}