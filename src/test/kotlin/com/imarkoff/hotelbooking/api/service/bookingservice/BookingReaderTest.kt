package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.dto.BookingDto
import com.imarkoff.hotelbooking.api.model.Booking
import com.imarkoff.hotelbooking.api.repository.BookingRepository
import com.imarkoff.hotelbooking.api.util.DtoFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class BookingReaderTest {
    @Mock
    private lateinit var bookingRepository: BookingRepository

    @Mock
    private lateinit var dtoFactory: DtoFactory

    @InjectMocks
    private lateinit var bookingReader: BookingReader

    @Test
    fun `getAllBookings should return all bookings`() {
        val booking1 = getMockBooking()
        val booking2 = getMockBooking()
        val bookingDto1 = getMockBookingDto()
        val bookingDto2 = getMockBookingDto()
        val bookings = listOf(booking1, booking2)
        whenever(bookingRepository.findAll()).thenReturn(bookings)
        whenever(dtoFactory.from(booking1)).thenReturn(bookingDto1)
        whenever(dtoFactory.from(booking2)).thenReturn(bookingDto2)

        val actualBookings = bookingReader.getAllBookings()

        val expectedBookings = listOf(bookingDto1, bookingDto2)
        assertEquals(expectedBookings, actualBookings)
    }

    @Test
    fun `getAllBookings should return empty list when no bookings are found`() {
        val bookings = emptyList<Booking>()
        whenever(bookingRepository.findAll()).thenReturn(bookings)

        val actualBookings = bookingReader.getAllBookings()

        val expectedBookings = emptyList<BookingDto>()
        assertEquals(expectedBookings, actualBookings)
    }

    @Test
    fun `getBookingById should return booking by ID`() {
        val bookingId = UUID.randomUUID()
        val booking = getMockBooking()
        val bookingDto = getMockBookingDto()
        whenever(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking))
        whenever(dtoFactory.from(booking)).thenReturn(bookingDto)

        val actualBooking = bookingReader.getBookingById(bookingId)

        assertEquals(bookingDto, actualBooking)
    }

    @Test
    fun `getBookingById should throw NoSuchElementException when booking not found`() {
        val bookingId = UUID.randomUUID()
        whenever(bookingRepository.findById(bookingId)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            bookingReader.getBookingById(bookingId)
        }
    }

    @Test
    fun `getBookingsByUserId should return bookings by user ID`() {
        val userId = UUID.randomUUID()
        val booking1 = getMockBooking()
        val booking2 = getMockBooking()
        val bookingDto1 = getMockBookingDto()
        val bookingDto2 = getMockBookingDto()
        val bookings = listOf(booking1, booking2)
        whenever(bookingRepository.findByUserId(userId)).thenReturn(bookings)
        whenever(dtoFactory.from(booking1)).thenReturn(bookingDto1)
        whenever(dtoFactory.from(booking2)).thenReturn(bookingDto2)

        val actualBookings = bookingReader.getBookingsByUserId(userId)

        val expectedBookings = listOf(bookingDto1, bookingDto2)
        assertEquals(expectedBookings, actualBookings)
    }

    @Test
    fun `getBookingsByUserId should return empty list when no bookings are found`() {
        val userId = UUID.randomUUID()
        val bookings = emptyList<Booking>()
        whenever(bookingRepository.findByUserId(userId)).thenReturn(bookings)

        val actualBookings = bookingReader.getBookingsByUserId(userId)

        val expectedBookings = emptyList<BookingDto>()
        assertEquals(expectedBookings, actualBookings)
    }
}