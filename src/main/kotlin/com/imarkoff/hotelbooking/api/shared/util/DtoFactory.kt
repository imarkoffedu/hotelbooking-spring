package com.imarkoff.hotelbooking.api.shared.util

import com.imarkoff.hotelbooking.api.booking.domain.Booking
import com.imarkoff.hotelbooking.api.booking.presentation.dtos.BookingDto
import com.imarkoff.hotelbooking.api.dto.*
import com.imarkoff.hotelbooking.api.model.*
import com.imarkoff.hotelbooking.api.user.domain.User
import com.imarkoff.hotelbooking.api.user.presentation.dtos.UserDto
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