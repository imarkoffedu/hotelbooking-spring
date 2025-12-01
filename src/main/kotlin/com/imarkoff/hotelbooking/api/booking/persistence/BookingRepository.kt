package com.imarkoff.hotelbooking.api.booking.persistence

import com.imarkoff.hotelbooking.api.booking.domain.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BookingRepository : JpaRepository<Booking, UUID> {
    fun findByUserId(userId: UUID): List<Booking>
    fun findByRoomNumber(roomNumber: Int): List<Booking>
}