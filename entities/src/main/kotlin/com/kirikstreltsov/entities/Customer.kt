package com.kirikstreltsov.entities

import jakarta.persistence.*

@Entity
@Table(name = "customers")
data class Customer(
    @Id
    val telegramId: Long,
    var username: String,
    @OneToMany(cascade = [(CascadeType.ALL)])
    val orders: List<Order>,
) {
    constructor(telegramId: Long, username: String) : this(telegramId, username, listOf())
}