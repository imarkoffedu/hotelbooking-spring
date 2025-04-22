package com.imarkoff.propertyagency.propertyagency.dto

import com.imarkoff.propertyagency.propertyagency.type.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Schema(description = "Booking information data transfer object")
@Serializable
data class BookingDto(
    @Schema(description = "Unique identifier of the booking", example = "123e4567-e89b-12d3-a456-426614174000")
    val id: UUIDString,

    @Schema(description = "ID of the user who made the booking", example = "123e4567-e89b-12d3-a456-426614174001")
    val userId: UUIDString,

    @Schema(description = "Room number for the booking", example = "101")
    val roomNumber: Int,

    @Schema(description = "Start date of the booking", example = "2023-10-15")
    val startDate: LocalDateString,

    @Schema(description = "End date of the booking", example = "2023-10-20")
    val endDate: LocalDateString,

    @Schema(description = "Current status of the booking", example = "CONFIRMED")
    val status: BookingStatus
)