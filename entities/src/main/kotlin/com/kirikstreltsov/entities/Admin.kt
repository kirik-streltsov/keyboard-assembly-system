package com.kirikstreltsov.entities

import jakarta.persistence.*

@Entity
@Table(name = "admins")
data class Admin(
    @Id
    val telegramId: Long,
    var username: String
)