package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.repository.BookingRepository
import com.imarkoff.hotelbooking.api.service.userservice.UserFinder
import com.imarkoff.hotelbooking.api.util.DtoFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.NoSuchElementException

@ExtendWith(MockitoExtension::class)
class BookingWriterTest {
    @Mock
    private lateinit var bookingRepository: BookingRepository

    @Mock
    private lateinit var userFinder: UserFinder

    @Mock
    private lateinit var bookingParser: BookingFormParser

    @Mock
    private lateinit var dtoFactory: DtoFactory

    @InjectMocks
    private lateinit var bookingWriter: BookingWriter

    @Test
    fun `createBooking should create a new booking`() {
        val bookingForm = getMockBookingFormDto()
        val booking = getMockBooking()
        val savedBooking = booking.copy()
        val bookingDto = getMockBookingDto()
        whenever(bookingParser.parse(bookingForm)).thenReturn(booking)
        whenever(bookingRepository.save(booking)).thenReturn(savedBooking)
        whenever(dtoFactory.from(savedBooking)).thenReturn(bookingDto)

        val result = bookingWriter.createBooking(bookingForm)

        assertEquals(bookingDto, result)
        verify(userFinder).findUserById(bookingForm.userId)
        verify(bookingParser).parse(bookingForm)
        verify(bookingRepository).save(booking)
    }

    @Test
    fun `createBooking should throw NoSuchElementException if user not found`() {
        val bookingForm = getMockBookingFormDto()
        whenever(userFinder.findUserById(bookingForm.userId)).thenThrow(NoSuchElementException("User not found"))

        assertThrows<NoSuchElementException> {
            bookingWriter.createBooking(bookingForm)
        }.apply {
            assertEquals("User not found", message)
        }
    }

    @Test
    fun `updateBooking should update an existing booking`() {
        val bookingId = UUID.randomUUID()
        val bookingForm = getMockBookingFormDto()
        val booking = getMockBooking()
        val updatedBooking = booking.copy()
        val bookingDto = getMockBookingDto()
        whenever(bookingRepository.existsById(bookingId)).thenReturn(true)
        whenever(bookingParser.parse(bookingForm, bookingId)).thenReturn(updatedBooking)
        whenever(bookingRepository.save(updatedBooking)).thenReturn(updatedBooking)
        whenever(dtoFactory.from(updatedBooking)).thenReturn(bookingDto)

        val result = bookingWriter.updateBooking(bookingId, bookingForm)

        assertEquals(bookingDto, result)
        verify(userFinder).findUserById(bookingForm.userId)
        verify(bookingParser).parse(bookingForm, bookingId)
        verify(bookingRepository).save(updatedBooking)
    }

    @Test
    fun `updateBooking should throw NoSuchElementException if booking not found`() {
        val bookingId = UUID.randomUUID()
        val bookingForm = getMockBookingFormDto()
        whenever(bookingRepository.existsById(bookingId)).thenReturn(false)

        assertThrows<NoSuchElementException> {
            bookingWriter.updateBooking(bookingId, bookingForm)
        }.apply {
            assertEquals("Booking with ID $bookingId not found", message)
        }
    }

    @Test
    fun `updateBooking should throw NoSuchElementException if user not found`() {
        val bookingId = UUID.randomUUID()
        val bookingForm = getMockBookingFormDto()
        whenever(userFinder.findUserById(bookingForm.userId)).thenThrow(NoSuchElementException("User not found"))

        assertThrows<NoSuchElementException> {
            bookingWriter.updateBooking(bookingId, bookingForm)
        }.apply {
            assertEquals("User not found", message)
        }
    }

    @Test
    fun `deleteBooking should delete an existing booking`() {
        val bookingId = UUID.randomUUID()
        whenever(bookingRepository.existsById(bookingId)).thenReturn(true)

        bookingWriter.deleteBooking(bookingId)

        verify(bookingRepository).deleteById(bookingId)
    }

    @Test
    fun `deleteBooking should throw NoSuchElementException if booking not found`() {
        val bookingId = UUID.randomUUID()
        whenever(bookingRepository.existsById(bookingId)).thenReturn(false)

        assertThrows<NoSuchElementException> {
            bookingWriter.deleteBooking(bookingId)
        }.apply {
            assertEquals("Booking with ID $bookingId not found", message)
        }
    }
}