package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.booking.presentation.dtos.BookingDto
import com.imarkoff.hotelbooking.api.booking.presentation.dtos.BookingFormDto
import com.imarkoff.hotelbooking.api.booking.domain.Booking
import com.imarkoff.hotelbooking.api.booking.domain.BookingStatus
import java.time.LocalDate
import java.util.*

fun getMockBooking() = Booking(
    id = UUID.randomUUID(),
    userId = UUID.randomUUID(),
    roomNumber = 101,
    startDate = LocalDate.now(),
    endDate = LocalDate.now().plusDays(2),
    status = BookingStatus.CONFIRMED
)

fun getMockBookingDto() = BookingDto(
    id = UUID.randomUUID().toString(),
    userId = UUID.randomUUID().toString(),
    roomNumber = 101,
    startDate = LocalDate.now().toString(),
    endDate = LocalDate.now().plusDays(2).toString(),
    status = BookingStatus.CONFIRMED
)

fun getMockBookingFormDto() = BookingFormDto(
    userId = UUID.randomUUID().toString(),
    roomNumber = 101,
    startDate = LocalDate.now().toString(),
    endDate = LocalDate.now().plusDays(2).toString(),
    status = BookingStatus.CONFIRMED
)