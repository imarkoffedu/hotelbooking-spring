package com.imarkoff.hotelbooking.api.controller.api

import com.imarkoff.hotelbooking.api.dto.ErrorResponse
import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.dto.UserFormDto
import com.imarkoff.hotelbooking.api.exception.ConflictException
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
@Tag(name = "User Management", description = "Endpoints for managing users in the property agency system")
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved all users",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = Array<UserDto>::class)
            )])
    ])
    @GetMapping("/")
    fun getAllUsers(): List<UserDto> {
        val users = userService.getAllUsers()
        return users
    }

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
        } catch (e: NoSuchElementException) {
            val errorResponse = ErrorResponse(404, "User not found")
            return ResponseEntity.status(404).body(errorResponse)
        }
    }

    @Operation(summary = "Create new user", description = "Creates a new user in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User successfully created",
            content = [Content(mediaType = "application/json",
                schema = Schema(implementation = UserFormDto::class)
            )]),
        ApiResponse(responseCode = "409", description = "User with the same email already exists"),
    ])
    @PostMapping("/")
    fun createUser(@RequestBody userFormDto: UserFormDto): ResponseEntity<Any> {
        try {
            val createdUser = userService.createUser(userFormDto)
            return ResponseEntity.status(201).body(createdUser)
        }
        catch (e: ConflictException) {
            val errorResponse = ErrorResponse(409, "User with the same email already exists")
            return ResponseEntity.status(409).body(errorResponse)
        }
    }
}