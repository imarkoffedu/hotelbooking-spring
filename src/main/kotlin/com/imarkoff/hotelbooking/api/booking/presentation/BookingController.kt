package com.imarkoff.hotelbooking.api.booking.presentation

import com.imarkoff.hotelbooking.api.booking.presentation.dtos.BookingDto
import com.imarkoff.hotelbooking.api.booking.presentation.dtos.BookingFormDto
import com.imarkoff.hotelbooking.api.shared.dto.ErrorResponse
import com.imarkoff.hotelbooking.api.booking.application.BookingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/bookings")
@SecurityRequirement(name = "basicScheme")
@Tag(name = "Booking Management", description = "Endpoints for managing hotel bookings")
@PreAuthorize("hasAnyRole('ADMIN', 'BOOKER')")
class BookingController(
    private val bookingService: BookingService
) {
    @Operation(summary = "Get all bookings", description = "Retrieves a list of all bookings in the system")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved all bookings",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = Array<BookingDto>::class)
            )]
        )
    ])
    @GetMapping("/")
    fun getAllBookings(): ResponseEntity<List<BookingDto>> {
        val bookings = bookingService.getAllBookings()
        return ResponseEntity.ok(bookings)
    }

    @Operation(summary = "Get booking by id", description = "Retrieves a booking by its unique ID")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved a booking",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BookingDto::class)
            )]
        ),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @GetMapping("/{id}")
    fun getBookingById(@PathVariable id: UUID): ResponseEntity<Any> {
        try {
            val booking = bookingService.getBookingById(id)
            return ResponseEntity.ok(booking)
        }
        catch (e: NoSuchElementException) {
            val errorResponse = ErrorResponse(404, "Booking not found")
            return ResponseEntity.status(404).body(errorResponse)
        }
    }

    @Operation(summary = "Get bookings by user ID", description = "Retrieves all bookings associated with a specific user")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved user's bookings",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = Array<BookingDto>::class)
            )]
        ),
        ApiResponse(responseCode = "404", description = "User not found")
    ])
    @GetMapping("/user/{userId}")
    fun getBookingsByUserId(@PathVariable userId: UUID): ResponseEntity<List<BookingDto>> {
        val bookings = bookingService.getBookingsByUserId(userId)
        return ResponseEntity.ok(bookings)
    }

    @Operation(summary = "Create new booking", description = "Creates a new booking in the system")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201", description = "Booking successfully created",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BookingFormDto::class)
            )]
        ),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(
            responseCode = "400", description = "Invalid input data",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    ])
    @PostMapping("/")
    fun createBooking(@RequestBody bookingForm: BookingFormDto) : ResponseEntity<Any> {
        return try {
            val booking = bookingService.createBooking(bookingForm)
            ResponseEntity.status(201).body(booking)
        } catch (e: NoSuchElementException) {
            val errorResponse = ErrorResponse(404, e.message ?: "Resource not found")
            ResponseEntity.status(404).body(errorResponse)
        } catch (e: IllegalArgumentException) {
            val errorResponse = ErrorResponse(400, e.message ?: "Invalid input")
            ResponseEntity.badRequest().body(errorResponse)
        }
    }

    @Operation(summary = "Update existing booking", description = "Updates a booking with the provided ID")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Booking successfully updated",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BookingFormDto::class)
            )]
        ),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @PutMapping("/{id}")
    fun updateBooking(
        @PathVariable id: UUID,
        @RequestBody bookingForm: BookingFormDto
    ) : ResponseEntity<Any> {
        return try {
            val updatedBooking = bookingService.updateBooking(id, bookingForm)
            ResponseEntity.ok(updatedBooking)
        } catch (e: NoSuchElementException) {
            val errorResponse = ErrorResponse(404, e.message ?: "Resource not found")
            ResponseEntity.status(404).body(errorResponse)
        } catch (e: IllegalArgumentException) {
            val errorResponse = ErrorResponse(400, e.message ?: "Invalid input")
            ResponseEntity.status(400).body(errorResponse)
        }
    }

    @Operation(summary = "Delete booking", description = "Deletes a booking with the specified ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Booking successfully deleted"),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @DeleteMapping("/{id}")
    fun deleteBooking(@PathVariable id: UUID) : ResponseEntity<Any> {
        return try {
            bookingService.deleteBooking(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            val errorResponse = ErrorResponse(404, e.message ?: "Resource not found")
            ResponseEntity.status(404).body(errorResponse)
        }
    }
}