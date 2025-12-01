package com.imarkoff.hotelbooking.api.user.presentation

import com.imarkoff.hotelbooking.api.shared.dto.ErrorResponse
import com.imarkoff.hotelbooking.api.user.presentation.dtos.UserDto
import com.imarkoff.hotelbooking.api.user.application.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.NoSuchElementException
import java.util.UUID

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users in the hotel system")
class UsersController(
    private val userService: UserService
) {
    @Operation(summary = "Get user by ID", description = "Retrieves a user with the specified ID")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved user",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = UserDto::class)
            )]
        ),
        ApiResponse(responseCode = "404", description = "User not found")
    ])
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<Any> {
        try {
            val user = userService.getUserById(id)
            return ResponseEntity.ok(user)
        } catch (_: NoSuchElementException) {
            val errorResponse = ErrorResponse(404, "User not found")
            return ResponseEntity.status(404).body(errorResponse)
        }
    }

    @Operation(summary = "Get user by email", description = "Retrieves a user with the specified email")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved user",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = UserDto::class)
            )]
        ),
        ApiResponse(responseCode = "404", description = "User not found")
    ])
    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<Any> {
        try {
            val user = userService.getUserByEmail(email)
            return ResponseEntity.ok(user)
        } catch (_: NoSuchElementException) {
            val errorResponse = ErrorResponse(404, "User not found")
            return ResponseEntity.status(404).body(errorResponse)
        }
    }
}