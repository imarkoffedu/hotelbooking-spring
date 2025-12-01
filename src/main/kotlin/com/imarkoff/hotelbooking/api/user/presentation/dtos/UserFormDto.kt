package com.imarkoff.hotelbooking.api.user.presentation.dtos

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Schema(description = "User data used for creating or updating a user")
@Serializable
data class UserFormDto(
    @Schema(description = "Name of the user", example = "John Doe")
    val name: String,

    @Schema(description = "Email of the user", example = "johndoe@example.com")
    val email: String,

    @Schema(description = "Password of the user", example = "password123")
    val password: String
)