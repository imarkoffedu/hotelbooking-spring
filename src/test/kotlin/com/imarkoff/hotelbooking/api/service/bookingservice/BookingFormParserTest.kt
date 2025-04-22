package com.imarkoff.hotelbooking.api.service.bookingservice

import com.imarkoff.hotelbooking.api.model.Booking
import com.imarkoff.hotelbooking.api.type.`typealias`.toLocalDate
import com.imarkoff.hotelbooking.api.type.`typealias`.toUUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class BookingFormParserTest {
    @InjectMocks
    private lateinit var bookingFormParser: BookingFormParser

    @Test
    fun `parse should convert BookingFormDto to Booking`() {
        val bookingForm = getMockBookingFormDto()
        val bookingId = UUID.randomUUID()
        val expectedBooking = Booking(
            id = bookingId,
            userId = bookingForm.userId.toUUID(),
            roomNumber = bookingForm.roomNumber,
            startDate = bookingForm.startDate.toLocalDate(),
            endDate = bookingForm.endDate.toLocalDate(),
            status = bookingForm.status
        )

        val result = bookingFormParser.parse(bookingForm, bookingId)

        assertEquals(expectedBooking, result)
    }

    @Test
    fun `parse should crate Booking with random ID if not provided`() {
        val bookingForm = getMockBookingFormDto()

        val result = bookingFormParser.parse(bookingForm)

        assertTrue(result.id.toString().isNotEmpty())
        assertTrue(result.id.toString().isNotBlank())
    }

    @Test
    fun `parse should throw IllegalArgumentException for invalid date format`() {
        val bookingForm = getMockBookingFormDto().copy(
            startDate = "invalid-date",
            endDate = "invalid-date"
        )

        assertThrows<IllegalArgumentException> {
            bookingFormParser.parse(bookingForm)
        }
    }
}