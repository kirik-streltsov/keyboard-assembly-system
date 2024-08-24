package com.kirikstreltsov.entities

import jakarta.persistence.*

@Entity
@Table(name = "keyboards")
data class Keyboard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    val case: Case,
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    val switch: Switch,
    @ManyToOne(cascade = [(CascadeType.MERGE)])
    val keycap: Keycap
) {
    constructor(case: Case, switch: Switch, keycap: Keycap) : this(0L, case, switch, keycap)
}