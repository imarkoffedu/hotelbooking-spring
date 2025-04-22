package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.dto.UserFormDto
import com.imarkoff.hotelbooking.api.exception.ConflictException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userFinder: UserFinder,
    private val userWriter: UserWriter
) {
    /**
     * Retrieves all users.
     * @return A list of UserDto objects representing all users.
     */
    fun getAllUsers() = userFinder.findAllUsers()

    /**
     * Retrieves a user by their ID.
     * @param id The UUID of the user to retrieve.
     * @return A UserDto object representing the user.
     * @throws NoSuchElementException if the user is not found.
     */
    fun getUserById(id: UUID) = userFinder.findUserById(id)

    /**
     * Creates a new user based on the provided user form data.
     * @param userFormDto The form data for the new user.
     * @return The created UserDto object.
     * @throws ConflictException if the user with the same email already exists.
     */
    fun createUser(userFormDto: UserFormDto) = userWriter.createUser(userFormDto)
}