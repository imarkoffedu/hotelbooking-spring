package com.imarkoff.propertyagency.propertyagency.dto

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Schema(description = "User information data transfer object")
@Serializable
data class UserDto(
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    val id: String,

    @Schema(description = "Name of the user", example = "John Doe")
    val name: String,

    @Schema(description = "Email of the user", example = "johndoe@example.com")
    val email: String
)