package com.imarkoff.propertyagency.propertyagency.service.bookingservice

import com.imarkoff.propertyagency.propertyagency.dto.BookingDto
import com.imarkoff.propertyagency.propertyagency.dto.BookingFormDto
import com.imarkoff.propertyagency.propertyagency.repository.BookingRepository
import com.imarkoff.propertyagency.propertyagency.service.userservice.UserFinder
import com.imarkoff.propertyagency.propertyagency.util.DtoFactory
import org.springframework.stereotype.Service
import java.util.*

/**
 * Provides methods for creating and updating bookings.
 */
@Service
class BookingWriter(
    private val bookingRepository: BookingRepository,
    private val userFinder: UserFinder,
    private val bookingParser: BookingFormParser,
    private val dtoFactory: DtoFactory
) {
    /**
     * Creates a new booking based on the provided booking form data.
     * @throws NoSuchElementException if the user is not found.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    fun createBooking(bookingForm: BookingFormDto): BookingDto {
        userFinder.findUserById(bookingForm.userId)
        val booking = bookingParser.parse(bookingForm)
        val savedBooking = bookingRepository.save(booking)
        return dtoFactory.from(savedBooking)
    }

    /**
     * Updates an existing booking with the provided booking ID and form data.
     * @throws NoSuchElementException if the booking or the user is not found.
     * @throws IllegalArgumentException if the booking ID is not a valid UUID.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    fun updateBooking(bookingId: UUID, bookingForm: BookingFormDto): BookingDto {
        userFinder.findUserById(bookingForm.userId)
        checkIfBookingExists(bookingId)
        val booking = bookingParser.parse(bookingForm, bookingId)
        val updatedBooking = bookingRepository.save(booking)
        return dtoFactory.from(updatedBooking)
    }

    /**
     * Deletes a booking with the specified ID.
     * @throws NoSuchElementException if the booking is not found.
     */
    fun deleteBooking(bookingId: UUID) {
        checkIfBookingExists(bookingId)
        bookingRepository.deleteById(bookingId)
    }

    private fun checkIfBookingExists(bookingId: UUID) {
        val exists = bookingRepository.existsById(bookingId)
        if (!exists) {
            throw NoSuchElementException("Booking with ID $bookingId not found")
        }
    }
}