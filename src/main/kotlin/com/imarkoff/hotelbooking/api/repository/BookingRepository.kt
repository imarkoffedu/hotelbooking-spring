package com.imarkoff.hotelbooking.api.repository

import com.imarkoff.hotelbooking.api.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookingRepository : JpaRepository<Booking, UUID> {
    fun findByUserId(userId: UUID): List<Booking>
    fun findByRoomNumber(roomNumber: Int): List<Booking>
}
