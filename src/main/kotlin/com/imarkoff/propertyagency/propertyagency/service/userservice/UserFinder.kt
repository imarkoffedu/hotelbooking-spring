package com.imarkoff.propertyagency.propertyagency.service.userservice

import com.imarkoff.propertyagency.propertyagency.model.User
import com.imarkoff.propertyagency.propertyagency.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserFinder(
    private val userRepository: UserRepository
) {
    /**
     * Finds a user by their ID.
     * @param userId The ID of the user to find.
     * @return The User object if found.
     * @throws NoSuchElementException if the user is not found.
     * @throws IllegalArgumentException if the userId is not a valid UUID.
     */
    fun findUserById(userId: String): User {
        try {
            val userUUID = UUID.fromString(userId)
            return getUserById(userUUID)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid user ID format: $userId")
        }
    }

    /**
     * Finds a user by their ID.
     * @param userId The UUID of the user to find.
     * @return The User object if found.
     * @throws NoSuchElementException if the user is not found.
     */
    fun findUserById(userId: UUID) = getUserById(userId)

    /**
     * Finds a user by their email.
     * @param email The email of the user to find.
     * @return The User object if found.
     * @throws NoSuchElementException if the user is not found.
     */
    fun findUserByEmail(email: String): User =
        userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User with email $email not found")

    private fun getUserById(userId: UUID): User =
        userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("User with ID $userId not found") }
}