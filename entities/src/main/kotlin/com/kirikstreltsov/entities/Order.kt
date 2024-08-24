package com.kirikstreltsov.entities

import jakarta.annotation.Nullable
import jakarta.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne(cascade = [(CascadeType.ALL)])
    val keyboard: Keyboard,
    @Enumerated(EnumType.STRING)
    var readinessState: ReadinessState,
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    val customer: Customer,
    @ManyToOne(cascade = [(CascadeType.ALL)])
    @Nullable
    var employee: Employee?
) {
    constructor(keyboard: Keyboard, customer: Customer)
            : this(0L, keyboard, ReadinessState.CREATED, customer, employee = null)
}