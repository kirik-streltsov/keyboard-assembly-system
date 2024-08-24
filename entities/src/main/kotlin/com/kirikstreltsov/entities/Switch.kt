package com.kirikstreltsov.entities

import jakarta.persistence.*

@Entity
@Table(name = "switches")
data class Switch(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    var price: Double
) {
    constructor(name: String, price: Double) : this(0, name, price)
}
