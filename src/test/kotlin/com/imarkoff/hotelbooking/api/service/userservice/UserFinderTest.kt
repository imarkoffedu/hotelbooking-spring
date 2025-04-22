package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.model.User
import com.imarkoff.hotelbooking.api.repository.UserRepository
import com.imarkoff.hotelbooking.api.util.DtoFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class UserFinderTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var dtoFactory: DtoFactory

    @InjectMocks
    private lateinit var userFinder: UserFinder

    @Test
    fun `findAllUsers should return all users`() {
        val user1 = getMockUser()
        val user2 = getMockUser()
        val users = listOf(user1, user2)
        val userDto1 = getMockUserDto()
        val userDto2 = getMockUserDto()
        val userDtos = listOf(userDto1, userDto2)
        whenever(userRepository.findAll()).thenReturn(users)
        whenever(dtoFactory.from(user1)).thenReturn(userDto1)
        whenever(dtoFactory.from(user2)).thenReturn(userDto2)

        val result = userFinder.findAllUsers()

        assertEquals(userDtos, result)
    }

    @Test
    fun `findAllUsers should return empty list when no users found`() {
        val users = emptyList<User>()
        val userDtos = emptyList<UserDto>()
        whenever(userRepository.findAll()).thenReturn(users)

        val result = userFinder.findAllUsers()

        assertEquals(userDtos, result)
    }

    @Test
    fun `findUserById should return user by UUID`() {
        val user = getMockUser()
        val userDto = getMockUserDto()
        val userId = user.id
        whenever(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user))
        whenever(dtoFactory.from(user)).thenReturn(userDto)

        val result = userFinder.findUserById(userId)

        assertEquals(userDto, result)
    }

    @Test
    fun `findUserById should throw NoSuchElementException when user not found`() {
        val userId = getMockUser().id
        whenever(userRepository.findById(userId)).thenReturn(java.util.Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            userFinder.findUserById(userId)
        }

        assertEquals("User with ID $userId not found", exception.message)
    }

    @Test
    fun `findUserById should return user if UUID is a String`() {
        val user = getMockUser()
        val userDto = getMockUserDto()
        val userId = user.id.toString()
        whenever(userRepository.findById(user.id)).thenReturn(java.util.Optional.of(user))
        whenever(dtoFactory.from(user)).thenReturn(userDto)

        val result = userFinder.findUserById(userId)

        assertEquals(userDto, result)
    }

    @Test
    fun `findUserByEmail should return user by email`() {
        val user = getMockUser()
        val email = user.email
        whenever(userRepository.findByEmail(email)).thenReturn(user)

        val result = userFinder.findUserByEmail(email)

        assertEquals(user, result)
    }

    @Test
    fun `findUserByEmail should throw NoSuchElementException if user with specified email was not found`() {
        val email = "not.existing@mail.com"
        whenever(userRepository.findByEmail(email)).thenReturn(null)

        assertThrows<NoSuchElementException> {
            userFinder.findUserByEmail(email)
        }
    }
}