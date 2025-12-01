package com.imarkoff.hotelbooking.api.auth.presentation

import com.imarkoff.hotelbooking.api.auth.presentation.dtos.AuthenticationRequest
import com.imarkoff.hotelbooking.api.auth.presentation.dtos.AuthenticationResponse
import com.imarkoff.hotelbooking.api.auth.application.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication Controller", description = "Handles user authentication requests")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
class AuthenticationController(
    private val service: AuthenticationService
) {
    @Operation(summary = "Authenticate User", description = "Authenticates a user and returns a JWT token")
    @PostMapping("/authenticate")
    fun authenticate(@RequestBody request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.authenticate(request))
    }
}