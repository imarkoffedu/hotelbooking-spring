package com.imarkoff.propertyagency.propertyagency.service.userservice

import com.imarkoff.propertyagency.propertyagency.dto.UserDto
import com.imarkoff.propertyagency.propertyagency.model.User
import com.imarkoff.propertyagency.propertyagency.repository.UserRepository
import com.imarkoff.propertyagency.propertyagency.type.`typealias`.UUIDString
import com.imarkoff.propertyagency.propertyagency.type.`typealias`.toUUID
import com.imarkoff.propertyagency.propertyagency.util.DtoFactory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserFinder(
    private val userRepository: UserRepository,
    private val dtoFactory: DtoFactory
) {
    private val logger = LoggerFactory.getLogger(UserFinder::class.java)

    /** Finds all users in the system. */
    fun findAllUsers(): List<UserDto> {
        logger.info("Finding all users")
        val users = userRepository.findAll()
        return users.map { dtoFactory.from(it) }
    }

    /**
     * Finds a user by their ID.
     * @param userId The ID of the user to find.
     * @return The User object if found.
     * @throws NoSuchElementException if the user is not found.
     * @throws IllegalArgumentException if the userId is not a valid UUID.
     */
    fun findUserById(userId: UUIDString): UserDto {
        logger.info("Finding user by string ID: $userId")
        try {
            val userUUID = userId.toUUID()
            return findUserById(userUUID)
        } catch (e: IllegalArgumentException) {
            logger.error("Invalid user ID format: $userId", e)
            throw IllegalArgumentException("Invalid user ID format: $userId")
        }
    }

    /**
     * Finds a user by their ID.
     * @param userId The UUID of the user to find.
     * @return The User object if found.
     * @throws NoSuchElementException if the user is not found.
     */
    fun findUserById(userId: UUID): UserDto {
        logger.info("Finding user by UUID: $userId")
        val result = getUserById(userId)
        return dtoFactory.from(result)
    }

    /**
     * Finds a user by their email.
     * @param email The email of the user to find.
     * @return The User object if found.
     * @throws NoSuchElementException if the user is not found.
     */
    fun findUserByEmail(email: String): User {
        logger.info("Finding user by email: $email")
        return userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User with email $email not found")
    }

    private fun getUserById(userId: UUID) =
        userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("User with ID $userId not found") }
}