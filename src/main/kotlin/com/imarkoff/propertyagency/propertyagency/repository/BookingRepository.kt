package com.imarkoff.propertyagency.propertyagency.repository

import com.imarkoff.propertyagency.propertyagency.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookingRepository : JpaRepository<Booking, UUID> {
    fun findByUserId(userId: UUID): List<Booking>
    fun findByRoomNumber(roomNumber: Int): List<Booking>
}
