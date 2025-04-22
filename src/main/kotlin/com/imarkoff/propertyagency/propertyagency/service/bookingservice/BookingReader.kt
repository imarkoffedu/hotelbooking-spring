package com.imarkoff.propertyagency.propertyagency.service.bookingservice

import com.imarkoff.propertyagency.propertyagency.dto.BookingDto
import com.imarkoff.propertyagency.propertyagency.repository.BookingRepository
import com.imarkoff.propertyagency.propertyagency.util.DtoFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookingReader(
    private val bookingRepository: BookingRepository,
    private val dtoFactory: DtoFactory
) {
    fun getAllBookings(): List<BookingDto> =
        bookingRepository.findAll()
            .map { dtoFactory.from(it) }

    fun getBookingById(id: UUID): BookingDto? =
        bookingRepository.findById(id)
            .map { dtoFactory.from(it) }
            .orElse(null)

    fun getBookingsByUserId(userId: UUID): List<BookingDto> =
        bookingRepository.findByUserId(userId)
            .map { dtoFactory.from(it) }
}