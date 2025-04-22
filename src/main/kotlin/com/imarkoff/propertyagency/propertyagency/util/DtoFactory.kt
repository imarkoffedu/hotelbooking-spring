package com.imarkoff.propertyagency.propertyagency.util

import com.imarkoff.propertyagency.propertyagency.dto.*
import com.imarkoff.propertyagency.propertyagency.model.*
import org.springframework.stereotype.Component

/**
 * Factory class for creating Data Transfer Objects (DTOs)
 * from domain models.
 */
@Component
class DtoFactory {
    fun from(user: User) = UserDto(
        id = user.id.toString(),
        name = user.name,
        email = user.email
    )

    fun from(booking: Booking) = BookingDto(
        id = booking.id.toString(),
        userId = booking.userId.toString(),
        roomNumber = booking.roomNumber,
        startDate = booking.startDate.toString(),
        endDate = booking.endDate.toString(),
        status = booking.status
    )
}