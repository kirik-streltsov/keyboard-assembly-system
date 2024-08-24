package com.kirikstreltsov.entities

import jakarta.persistence.*

@Entity
@Table(name = "employees")
data class Employee(
    @Id
    val telegramId: Long,
    var username: String,
    var approved: Boolean,
    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JoinTable(
        name = "employee_orders", joinColumns = [(JoinColumn(name = "employee_id"))],
        inverseJoinColumns = [(JoinColumn(name = "order_id"))]
    )
    var orders: List<Order>
) {
    constructor(telegramId: Long, username: String, approved: Boolean = false)
            : this(telegramId, username, approved, emptyList())
}