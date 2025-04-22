package com.imarkoff.hotelbooking.api.model

import com.imarkoff.hotelbooking.api.type.BookingStatus
import java.time.LocalDate
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "bookings")
data class Booking (
    @Id
    @Column(name = "id")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val userId: UUID,

    @Column(nullable = false)
    val roomNumber: Int,

    @Column(nullable = false)
    val startDate: LocalDate,

    @Column(nullable = false)
    val endDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: BookingStatus
)