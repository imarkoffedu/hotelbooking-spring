package com.imarkoff.propertyagency.propertyagency.service.bookingservice

import com.imarkoff.propertyagency.propertyagency.dto.BookingDto
import com.imarkoff.propertyagency.propertyagency.dto.BookingFormDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookingService(
    private val bookingReader: BookingReader,
    private val bookingWriter: BookingWriter
) {
    /** Retrieves all bookings. */
    fun getAllBookings() =
        bookingReader.getAllBookings()

    /** Retrieves a booking by its ID. */
    fun getBookingById(id: UUID) =
        bookingReader.getBookingById(id)

    /** Retrieves bookings by user ID. */
    fun getBookingsByUserId(userId: UUID) =
        bookingReader.getBookingsByUserId(userId)

    /**
     * Creates a new booking based on the provided booking form data.
     * @throws NoSuchElementException if the user is not found.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    fun createBooking(bookingForm: BookingFormDto): BookingDto =
        bookingWriter.createBooking(bookingForm)

    /**
     * Updates an existing booking with the provided booking ID and form data.
     * @throws NoSuchElementException if the booking or the user is not found.
     * @throws IllegalArgumentException if the booking ID is not a valid UUID.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    fun updateBooking(id: UUID, bookingForm: BookingFormDto): BookingDto =
        bookingWriter.updateBooking(id, bookingForm)

    /**
     * Deletes a booking with the specified ID.
     * @throws NoSuchElementException if the booking is not found.
     */
    fun deleteBooking(id: UUID) {
        bookingWriter.deleteBooking(id)
    }
}