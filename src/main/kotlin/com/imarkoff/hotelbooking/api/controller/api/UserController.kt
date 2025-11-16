package com.imarkoff.hotelbooking.api.controller.api

import com.imarkoff.hotelbooking.api.dto.ErrorResponse
import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.service.userservice.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users in the hotel system")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "Get user by ID", description = "Retrieves a user with the specified ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved user",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = UserDto::class)
            )]),
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
        ApiResponse(responseCode = "200", description = "Successfully retrieved user",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = UserDto::class)
            )]),
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