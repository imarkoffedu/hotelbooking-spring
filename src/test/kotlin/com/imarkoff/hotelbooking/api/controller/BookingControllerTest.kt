package com.imarkoff.hotelbooking.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.imarkoff.hotelbooking.api.controller.api.BookingController
import com.imarkoff.hotelbooking.api.dto.BookingDto
import com.imarkoff.hotelbooking.api.service.bookingservice.*
import com.imarkoff.hotelbooking.api.type.`typealias`.toUUID
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.Test



@WebMvcTest(BookingController::class)
class BookingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var bookingService: BookingService

    @Test
    fun `getAllBookings returns all bookings`() {
        val bookings = listOf(getMockBookingDto(), getMockBookingDto())
        whenever(bookingService.getAllBookings()).thenReturn(bookings)

        mockMvc.perform(get("/bookings/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(bookings[0].id))
            .andExpect(jsonPath("$[1].id").value(bookings[1].id))
    }

    @Test
    fun `getAllBookings returns empty list when no bookings exist`() {
        val bookings = emptyList<BookingDto>()
        whenever(bookingService.getAllBookings()).thenReturn(bookings)

        mockMvc.perform(get("/bookings/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `getBookingById returns booking if it exists`() {
        val booking = getMockBookingDto()
        whenever(bookingService.getBookingById(booking.id.toUUID())).thenReturn(booking)

        mockMvc.perform(get("/bookings/${booking.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(booking.id))
    }

    @Test
    fun `getBookingById returns 404 if booking does not exist`() {
        val bookingId = UUID.randomUUID()
        whenever(bookingService.getBookingById(bookingId)).thenThrow(NoSuchElementException())

        mockMvc.perform(get("/bookings/$bookingId"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Booking not found"))
    }

    @Test
    fun `getBookingsByUserId returns bookings for a user`() {
        val userId = UUID.randomUUID()
        val bookings = listOf(getMockBookingDto(), getMockBookingDto())
        whenever(bookingService.getBookingsByUserId(userId)).thenReturn(bookings)

        mockMvc.perform(get("/bookings/user/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(bookings[0].id))
            .andExpect(jsonPath("$[1].id").value(bookings[1].id))
    }

    @Test
    fun `getBookingsByUserId returns empty list when no bookings exist for user`() {
        val userId = UUID.randomUUID()
        val bookings = emptyList<BookingDto>()
        whenever(bookingService.getBookingsByUserId(userId)).thenReturn(bookings)

        mockMvc.perform(get("/bookings/user/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `createBooking returns created booking`() {
        val bookingForm = getMockBookingFormDto()
        val booking = getMockBookingDto()
        whenever(bookingService.createBooking(bookingForm)).thenReturn(booking)

        mockMvc.perform(post("/bookings/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(booking.id))
    }

    @Test
    fun `createBooking returns 404 if user does not exist`() {
        val bookingForm = getMockBookingFormDto()
        whenever(bookingService.createBooking(bookingForm))
            .thenThrow(NoSuchElementException("User not found"))

        mockMvc.perform(post("/bookings/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("User not found"))
    }

    @Test
    fun `createBooking returns 400 if input data is invalid`() {
        val bookingForm = getMockBookingFormDto()
        whenever(bookingService.createBooking(bookingForm))
            .thenThrow(IllegalArgumentException("Invalid input data"))

        mockMvc.perform(post("/bookings/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid input data"))
    }

    @Test
    fun `updateBooking returns updated booking`() {
        val bookingForm = getMockBookingFormDto()
        val updatedBooking = getMockBookingDto()
        val bookingId = updatedBooking.id.toUUID()
        whenever(bookingService.updateBooking(bookingId, bookingForm)).thenReturn(updatedBooking)

        mockMvc.perform(put("/bookings/$bookingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(updatedBooking.id))
    }

    @Test
    fun `updateBooking returns 404 if booking does not exist`() {
        val bookingForm = getMockBookingFormDto()
        val bookingId = UUID.randomUUID()
        whenever(bookingService.updateBooking(bookingId, bookingForm))
            .thenThrow(NoSuchElementException("Booking not found"))

        mockMvc.perform(put("/bookings/$bookingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Booking not found"))
    }

    @Test
    fun `updateBooking returns 400 if input data is invalid`() {
        val bookingForm = getMockBookingFormDto()
        val bookingId = UUID.randomUUID()
        whenever(bookingService.updateBooking(bookingId, bookingForm))
            .thenThrow(IllegalArgumentException("Invalid input data"))

        mockMvc.perform(put("/bookings/$bookingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid input data"))
    }

    @Test
    fun `deleteBooking returns 204 if booking is deleted successfully`() {
        val bookingId = UUID.randomUUID()
        doNothing().whenever(bookingService).deleteBooking(bookingId)

        mockMvc.perform(delete("/bookings/$bookingId"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `deleteBooking returns 404 if booking does not exist`() {
        val bookingId = UUID.randomUUID()
        whenever(bookingService.deleteBooking(bookingId))
            .thenThrow(NoSuchElementException("Booking not found"))

        mockMvc.perform(delete("/bookings/$bookingId"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Booking not found"))
    }
}