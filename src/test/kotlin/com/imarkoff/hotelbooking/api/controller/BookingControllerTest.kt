package com.imarkoff.hotelbooking.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.imarkoff.hotelbooking.api.booking.application.BookingService
import com.imarkoff.hotelbooking.api.booking.presentation.BookingController
import com.imarkoff.hotelbooking.api.booking.presentation.dtos.BookingDto
import com.imarkoff.hotelbooking.api.service.bookingservice.*
import com.imarkoff.hotelbooking.api.shared.`typealias`.toUUID
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
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
    fun `getAllBookings throws unauthorized without authentication`() {
        mockMvc.perform(get("/bookings/"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `getAllBookings returns all bookings`() {
        val bookings = listOf(getMockBookingDto(), getMockBookingDto())
        whenever(bookingService.getAllBookings()).thenReturn(bookings)

        mockMvc.perform(get("/bookings/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(bookings[0].id))
            .andExpect(jsonPath("$[1].id").value(bookings[1].id))
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `getAllBookings returns empty list when no bookings exist`() {
        val bookings = emptyList<BookingDto>()
        whenever(bookingService.getAllBookings()).thenReturn(bookings)

        mockMvc.perform(get("/bookings/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `getBookingById returns booking if it exists`() {
        val booking = getMockBookingDto()
        whenever(bookingService.getBookingById(booking.id.toUUID())).thenReturn(booking)

        mockMvc.perform(get("/bookings/${booking.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(booking.id))
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `getBookingById returns 404 if booking does not exist`() {
        val bookingId = UUID.randomUUID()
        whenever(bookingService.getBookingById(bookingId)).thenThrow(NoSuchElementException())

        mockMvc.perform(get("/bookings/$bookingId"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Booking not found"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
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
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `getBookingsByUserId returns empty list when no bookings exist for user`() {
        val userId = UUID.randomUUID()
        val bookings = emptyList<BookingDto>()
        whenever(bookingService.getBookingsByUserId(userId)).thenReturn(bookings)

        mockMvc.perform(get("/bookings/user/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `createBooking returns created booking`() {
        val bookingForm = getMockBookingFormDto()
        val booking = getMockBookingDto()
        whenever(bookingService.createBooking(bookingForm)).thenReturn(booking)

        mockMvc.perform(post("/bookings/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
            .with(csrf())
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(booking.id))
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `createBooking returns 404 if user does not exist`() {
        val bookingForm = getMockBookingFormDto()
        whenever(bookingService.createBooking(bookingForm))
            .thenThrow(NoSuchElementException("User not found"))

        mockMvc.perform(post("/bookings/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
            .with(csrf())
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("User not found"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `createBooking returns 400 if input data is invalid`() {
        val bookingForm = getMockBookingFormDto()
        whenever(bookingService.createBooking(bookingForm))
            .thenThrow(IllegalArgumentException("Invalid input data"))

        mockMvc.perform(post("/bookings/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
            .with(csrf())
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid input data"))
    }

    @Test
    @WithMockUser(roles = ["BOOKER"])
    fun `updateBooking returns updated booking`() {
        val bookingForm = getMockBookingFormDto()
        val updatedBooking = getMockBookingDto()
        val bookingId = updatedBooking.id.toUUID()
        whenever(bookingService.updateBooking(bookingId, bookingForm)).thenReturn(updatedBooking)

        mockMvc.perform(put("/bookings/$bookingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
            .with(csrf())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(updatedBooking.id))
    }

    @Test
    @WithMockUser(roles = ["BOOKER"])
    fun `updateBooking returns 404 if booking does not exist`() {
        val bookingForm = getMockBookingFormDto()
        val bookingId = UUID.randomUUID()
        whenever(bookingService.updateBooking(bookingId, bookingForm))
            .thenThrow(NoSuchElementException("Booking not found"))

        mockMvc.perform(put("/bookings/$bookingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
            .with(csrf())
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Booking not found"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `updateBooking returns 400 if input data is invalid`() {
        val bookingForm = getMockBookingFormDto()
        val bookingId = UUID.randomUUID()
        whenever(bookingService.updateBooking(bookingId, bookingForm))
            .thenThrow(IllegalArgumentException("Invalid input data"))

        mockMvc.perform(put("/bookings/$bookingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookingForm))
            .with(csrf())
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid input data"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `deleteBooking returns 204 if booking is deleted successfully`() {
        val bookingId = UUID.randomUUID()
        doNothing().whenever(bookingService).deleteBooking(bookingId)

        mockMvc.perform(delete("/bookings/$bookingId")
            .with(csrf())
        )
            .andExpect(status().isNoContent)
    }

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `deleteBooking returns 404 if booking does not exist`() {
        val bookingId = UUID.randomUUID()
        whenever(bookingService.deleteBooking(bookingId))
            .thenThrow(NoSuchElementException("Booking not found"))

        mockMvc.perform(delete("/bookings/$bookingId")
            .with(csrf())
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Booking not found"))
    }
}