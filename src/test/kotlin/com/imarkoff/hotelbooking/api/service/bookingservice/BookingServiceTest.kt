package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.dto.BookingFormDto
import com.imarkoff.hotelbooking.api.type.BookingStatus
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.kotlin.verify
import java.time.LocalDate
import java.util.*
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class BookingServiceTest {
    @Mock
    private lateinit var bookingReader: BookingReader

    @Mock
    private lateinit var bookingWriter: BookingWriter

    @InjectMocks
    private lateinit var bookingService: BookingService

    @Test
    fun `getAllBookings should delegate to bookingReader`() {
        val expectedBookings = listOf(
            getMockBookingDto(), getMockBookingDto(), getMockBookingDto()
        )
        whenever(bookingReader.getAllBookings()).thenReturn(expectedBookings)

        val result = bookingService.getAllBookings()

        assertEquals(expectedBookings, result)
        verify(bookingReader).getAllBookings()
    }

    @Test
    fun `getBookingById should delegate to bookingReader`() {
        val bookingId = UUID.randomUUID()
        val expectedBooking = getMockBookingDto()
        whenever(bookingReader.getBookingById(bookingId)).thenReturn(expectedBooking)

        val result = bookingService.getBookingById(bookingId)

        assertEquals(expectedBooking, result)
        verify(bookingReader).getBookingById(bookingId)
    }

    @Test
    fun `getBookingsByUserId should delegate to bookingReader`() {
        val userId = UUID.randomUUID()
        val expectedBookings = listOf(getMockBookingDto())
        whenever(bookingReader.getBookingsByUserId(userId)).thenReturn(expectedBookings)

        val result = bookingService.getBookingsByUserId(userId)

        assertEquals(expectedBookings, result)
        verify(bookingReader).getBookingsByUserId(userId)
    }

    @Test
    fun `createBooking should delegate to bookingWriter`() {
        val bookingForm = BookingFormDto(
            userId = UUID.randomUUID().toString(),
            roomNumber = 101,
            startDate = LocalDate.now().toString(),
            endDate = LocalDate.now().plusDays(2).toString(),
            status = BookingStatus.CONFIRMED
        )
        val expectedBooking = getMockBookingDto()
        whenever(bookingWriter.createBooking(bookingForm)).thenReturn(expectedBooking)

        val result = bookingService.createBooking(bookingForm)

        assertEquals(expectedBooking, result)
        verify(bookingWriter).createBooking(bookingForm)
    }

    @Test
    fun `updateBooking should delegate to bookingWriter`() {
        val bookingId = UUID.randomUUID()
        val bookingForm = BookingFormDto(
            userId = UUID.randomUUID().toString(),
            roomNumber = 101,
            startDate = LocalDate.now().toString(),
            endDate = LocalDate.now().plusDays(2).toString(),
            status = BookingStatus.CONFIRMED
        )
        val expectedBooking = getMockBookingDto()
        whenever(bookingWriter.updateBooking(bookingId, bookingForm)).thenReturn(expectedBooking)

        val result = bookingService.updateBooking(bookingId, bookingForm)

        assertEquals(expectedBooking, result)
        verify(bookingWriter).updateBooking(bookingId, bookingForm)
    }

    @Test
    fun `deleteBooking should delegate to bookingWriter`() {
        val bookingId = UUID.randomUUID()

        bookingService.deleteBooking(bookingId)

        verify(bookingWriter).deleteBooking(bookingId)
    }
}