package com.imarkoff.propertyagency.propertyagency.service.bookingservice

import com.imarkoff.propertyagency.propertyagency.dto.BookingDto
import com.imarkoff.propertyagency.propertyagency.repository.BookingRepository
import com.imarkoff.propertyagency.propertyagency.util.DtoFactory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookingReader(
    private val bookingRepository: BookingRepository,
    private val dtoFactory: DtoFactory
) {
    private val logger = LoggerFactory.getLogger(BookingReader::class.java.name)

    fun getAllBookings(): List<BookingDto> {
        logger.info("Retrieving all bookings")
        return bookingRepository.findAll()
            .map { dtoFactory.from(it) }
    }

    fun getBookingById(id: UUID): BookingDto? {
        logger.info("Retrieving booking with ID $id")
        return bookingRepository.findById(id)
            .map { dtoFactory.from(it) }
            .orElse(null)
    }

    fun getBookingsByUserId(userId: UUID): List<BookingDto> {
        logger.info("Retrieving bookings for user ID $userId")
        return bookingRepository.findByUserId(userId)
            .map { dtoFactory.from(it) }
    }
}