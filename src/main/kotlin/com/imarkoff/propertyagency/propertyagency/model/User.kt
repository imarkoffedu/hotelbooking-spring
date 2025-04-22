package com.imarkoff.propertyagency.propertyagency.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
data class User (
    @Id
    @Column(name = "id")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String
)