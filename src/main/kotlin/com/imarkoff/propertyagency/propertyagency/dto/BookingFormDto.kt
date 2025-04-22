package com.imarkoff.propertyagency.propertyagency.dto

import com.imarkoff.propertyagency.propertyagency.type.BookingStatus
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Schema(description = "Booking form data transfer object")
@Serializable
data class BookingFormDto(
    @Schema(description = "ID of the user who made the booking", example = "123e4567-e89b-12d3-a456-426614174001")
    val userId: String,
    @Schema(description = "Room number for the booking", example = "101")
    val roomNumber: Int,
    @Schema(description = "Start date of the booking", example = "2023-10-15")
    val startDate: String,
    @Schema(description = "End date of the booking", example = "2023-10-20")
    val endDate: String,
    @Schema(description = "Current status of the booking", example = "CONFIRMED")
    val status: BookingStatus
)