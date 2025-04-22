package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.dto.BookingDto
import com.imarkoff.hotelbooking.api.dto.BookingFormDto
import com.imarkoff.hotelbooking.api.model.Booking
import com.imarkoff.hotelbooking.api.type.BookingStatus
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