package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.dto.BookingDto
import com.imarkoff.hotelbooking.api.dto.BookingFormDto
import com.imarkoff.hotelbooking.api.repository.BookingRepository
import com.imarkoff.hotelbooking.api.service.userservice.UserFinder
import com.imarkoff.hotelbooking.api.util.DtoFactory
import org.springframework.stereotype.Service
import java.util.*
import org.slf4j.LoggerFactory

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
    private val logger = LoggerFactory.getLogger(BookingWriter::class.java.name)
    /**
     * Creates a new booking based on the provided booking form data.
     * @throws NoSuchElementException if the user is not found.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    fun createBooking(bookingForm: BookingFormDto): BookingDto {
        logger.info("Creating booking with for the user ID ${bookingForm.userId}")
        checkIfUserExists(bookingForm.userId)
        val booking = bookingParser.parse(bookingForm)
        val savedBooking = bookingRepository.save(booking)
        logger.info("Booking created with ID ${savedBooking.id} for the user ID ${bookingForm.userId}")
        return dtoFactory.from(savedBooking)
    }

    /**
     * Updates an existing booking with the provided booking ID and form data.
     * @throws NoSuchElementException if the booking or the user is not found.
     * @throws IllegalArgumentException if the booking ID is not a valid UUID.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    fun updateBooking(bookingId: UUID, bookingForm: BookingFormDto): BookingDto {
        logger.info("Updating booking with ID $bookingId for the user ID ${bookingForm.userId}")
        checkIfUserExists(bookingForm.userId)
        checkIfBookingExists(bookingId)
        val booking = bookingParser.parse(bookingForm, bookingId)
        val updatedBooking = bookingRepository.save(booking)
        logger.info("Booking updated with ID $bookingId for the user ID ${bookingForm.userId}")
        return dtoFactory.from(updatedBooking)
    }

    /**
     * Deletes a booking with the specified ID.
     * @throws NoSuchElementException if the booking is not found.
     */
    fun deleteBooking(bookingId: UUID) {
        logger.info("Deleting booking with ID $bookingId")
        checkIfBookingExists(bookingId)
        bookingRepository.deleteById(bookingId)
        logger.info("Booking with ID $bookingId deleted")
    }

    private fun checkIfUserExists(userId: String) {
        logger.debug("Checking if user with ID $userId exists")
        userFinder.findUserById(userId)
    }

    private fun checkIfBookingExists(bookingId: UUID) {
        logger.debug("Checking if booking with ID {} exists", bookingId)
        val exists = bookingRepository.existsById(bookingId)
        if (!exists) {
            logger.warn("Booking with ID $bookingId not found")
            throw NoSuchElementException("Booking with ID $bookingId not found")
        }
    }
}