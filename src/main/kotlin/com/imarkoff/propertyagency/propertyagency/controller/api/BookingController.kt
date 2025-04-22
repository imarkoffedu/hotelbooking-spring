package com.imarkoff.propertyagency.propertyagency.controller.api

import com.imarkoff.propertyagency.propertyagency.dto.BookingDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking Management", description = "Endpoints for managing property bookings")
class BookingController {
    @Operation(summary = "Get all bookings", description = "Retrieves a list of all bookings in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved all bookings",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = Array<BookingDto>::class))])
    ])
    @GetMapping("/")
    fun getAllBookings() {
        throw NotImplementedError()
    }

    @Operation(summary = "Get all bookings", description = "Retrieves a list of all bookings in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved all bookings",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = BookingDto::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized request")
    ])
    @GetMapping("/{id}")
    fun getBookingById(@PathVariable id: UUID) {
        throw NotImplementedError()
    }

    @Operation(summary = "Create new booking", description = "Creates a new booking in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Booking successfully created",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = BookingDto::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input data")
    ])
    @PostMapping("/")
    fun createBooking() {
        throw NotImplementedError()
    }

    @Operation(summary = "Update existing booking", description = "Updates a booking with the provided ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Booking successfully updated",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = BookingDto::class))]),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @PutMapping("/{id}")
    fun updateBooking(@PathVariable id: Long) {
        throw NotImplementedError()
    }

    @Operation(summary = "Delete booking", description = "Deletes a booking with the specified ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Booking successfully deleted"),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @DeleteMapping("/{id}")
    fun deleteBooking(@PathVariable id: UUID) {
        throw NotImplementedError()
    }

    @Operation(summary = "Get bookings by user ID", description = "Retrieves all bookings associated with a specific user")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved user's bookings",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = Array<BookingDto>::class))]),
        ApiResponse(responseCode = "404", description = "User not found")
    ])
    @GetMapping("/user/{userId}")
    fun getBookingsByUserId(@PathVariable userId: UUID) {
        throw NotImplementedError()
    }
}