package com.imarkoff.hotelbooking.api.controller.api

import com.imarkoff.hotelbooking.api.dto.ErrorResponse
import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.dto.UserFormDto
import com.imarkoff.hotelbooking.api.exception.ConflictException
import com.imarkoff.hotelbooking.api.service.userservice.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/users")
@Tag(name = "Users Management from the admin side", description = "Endpoints for managing users from the admin side")
class AdminUsersController(
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
        catch (_: ConflictException) {
            val errorResponse = ErrorResponse(409, "User with the same email already exists")
            return ResponseEntity.status(409).body(errorResponse)
        }
    }
}