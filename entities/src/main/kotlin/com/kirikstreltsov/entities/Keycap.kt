package com.kirikstreltsov.entities

import jakarta.persistence.*

@Entity
@Table(name = "keycaps")
data class Keycap(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    var price: Double,
) {
    constructor(name: String, price: Double) : this(0, name, price)
}
