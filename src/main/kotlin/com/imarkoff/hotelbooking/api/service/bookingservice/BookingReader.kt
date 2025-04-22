package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.dto.BookingDto
import com.imarkoff.hotelbooking.api.repository.BookingRepository
import com.imarkoff.hotelbooking.api.util.DtoFactory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookingReader(
    private val bookingRepository: BookingRepository,
    private val dtoFactory: DtoFactory
) {
    private val logger = LoggerFactory.getLogger(BookingReader::class.java.name)

    /**
     * Retrieves all bookings.
     * @return a list of BookingDto objects.
     */
    fun getAllBookings(): List<BookingDto> {
        logger.info("Retrieving all bookings")
        return bookingRepository.findAll()
            .map { dtoFactory.from(it) }
    }

    /**
     * Retrieves a booking by its ID.
     * @throws NoSuchElementException if the booking is not found.
     */
    fun getBookingById(id: UUID): BookingDto {
        logger.info("Retrieving booking with ID $id")
        return bookingRepository.findById(id)
            .map { dtoFactory.from(it) }
            .orElseThrow {
                NoSuchElementException("Booking with ID $id not found")
            }
    }

    /**
     * Retrieves bookings by user ID.
     * @return a list of BookingDto objects.
     */
    fun getBookingsByUserId(userId: UUID): List<BookingDto> {
        logger.info("Retrieving bookings for user ID $userId")
        return bookingRepository.findByUserId(userId)
            .map { dtoFactory.from(it) }
    }
}