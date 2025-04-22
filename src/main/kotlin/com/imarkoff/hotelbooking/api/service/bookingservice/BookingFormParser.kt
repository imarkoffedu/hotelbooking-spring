package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.dto.BookingFormDto
import com.imarkoff.hotelbooking.api.model.Booking
import com.imarkoff.hotelbooking.api.type.`typealias`.toLocalDate
import com.imarkoff.hotelbooking.api.type.`typealias`.toUUID
import org.springframework.stereotype.Service
import java.time.format.DateTimeParseException
import java.util.*

@Service
class BookingFormParser {
    /**
     * Parses a BookingFormDto into a Booking object.
     * @param bookingForm The BookingFormDto to parse.
     * @param bookingId Optional booking ID for updates.
     * @return The Booking object.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    fun parse(bookingForm: BookingFormDto, bookingId: UUID? = null): Booking =
        tryBookingFormToBooking(bookingForm, bookingId)

    private fun tryBookingFormToBooking(bookingForm: BookingFormDto, bookingId: UUID?): Booking {
        try {
            return bookingFormDtoToBooking(bookingForm, bookingId)
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Invalid date format: ${bookingForm.startDate} or ${bookingForm.endDate}")
        }
    }

    private fun bookingFormDtoToBooking(bookingForm: BookingFormDto, bookingId: UUID?): Booking {
        val id = bookingId ?: UUID.randomUUID()
        val userId = bookingForm.userId.toUUID()
        val startDate = bookingForm.startDate.toLocalDate()
        val endDate = bookingForm.endDate.toLocalDate()
        return Booking(
            id = id,
            userId = userId,
            roomNumber = bookingForm.roomNumber,
            startDate = startDate,
            endDate = endDate,
            status = bookingForm.status
        )
    }
}