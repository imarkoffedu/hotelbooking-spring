package com.imarkoff.propertyagency.propertyagency.controller.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users in the property agency system")
class UserController {

    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved all users")
    ])
    @GetMapping("/")
    fun getAllUsers() {
        throw NotImplementedError()
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user with the specified ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        ApiResponse(responseCode = "404", description = "User not found")
    ])
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID) {
        throw NotImplementedError()
    }

    @Operation(summary = "Create new user", description = "Creates a new user in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User successfully created"),
        ApiResponse(responseCode = "400", description = "Invalid input data")
    ])
    @PostMapping("/")
    fun createUser() {
        throw NotImplementedError()
    }
}