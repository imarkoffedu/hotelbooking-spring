package com.imarkoff.hotelbooking.api.auth.presentation.dtos

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/*
  @author   george
  @project   security25
  @class  AuthenticationRequest
  @version  1.0.0
  @since 15.04.25 - 20.27
*/


@Schema(description = "Authentication request containing user login and password")
@Serializable
data class AuthenticationRequest(

    @Schema(description = "User login", example = "johndoe@example.com")
    val login: String,

    @Schema(description = "User password", example = "password123")
    val password: String,
)