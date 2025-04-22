package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.dto.UserFormDto
import com.imarkoff.hotelbooking.api.exception.ConflictException
import com.imarkoff.hotelbooking.api.repository.UserRepository
import com.imarkoff.hotelbooking.api.util.DtoFactory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserWriter(
    private val userRepository: UserRepository,
    private val userFinder: UserFinder,
    private val userFormParser: UserFormParser,
    private val dtoFactory: DtoFactory
) {
    private val logger = LoggerFactory.getLogger(UserWriter::class.java)

    /**
     * Creates a new user based on the provided user form data.
     * @param userFormDto The form data for the new user.
     * @return The created UserDto object.
     * @throws ConflictException if the user with the same email already exists.
     */
    fun createUser(userFormDto: UserFormDto): UserDto {
        logger.info("Creating user with email: ${userFormDto.email}")
        checkIfUserWithEmailExists(userFormDto.email)
        val user = userFormParser.parse(userFormDto)
        userRepository.save(user)
        logger.info("User created with ID: ${user.id}")
        return dtoFactory.from(user)
    }

    private fun checkIfUserWithEmailExists(email: String) {
        logger.info("Checking if user with email $email exists")
        try {
            userFinder.findUserByEmail(email)
        }
        catch (e: NoSuchElementException) {
            logger.info("User with email $email does not exist")
            return
        }
        logger.warn("User with email $email already exists")
        throw ConflictException("User with email $email already exists")
    }
}