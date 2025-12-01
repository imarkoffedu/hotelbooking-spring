package com.imarkoff.hotelbooking.api.auth.presentation.dtos

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/*
  @author   george
  @project   security25
  @class  AuthenticationResponse
  @version  1.0.0
  @since 15.04.25 - 20.32
*/

@Schema(description = "Authentication response containing JWT token")
@Serializable
data class AuthenticationResponse (

    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val token: String
)