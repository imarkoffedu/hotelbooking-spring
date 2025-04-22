package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.exception.ConflictException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock
    private lateinit var userFinder: UserFinder

    @Mock
    private lateinit var userWriter: UserWriter

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `getAllUsers returns all users`() {
        val expectedResult = listOf(getMockUserDto(), getMockUserDto())
        whenever(userFinder.findAllUsers()).thenReturn(expectedResult)

        val actualResult = userService.getAllUsers()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `getUserById returns user by ID`() {
        val userId = UUID.randomUUID()
        val expectedResult = getMockUserDto()
        whenever(userFinder.findUserById(userId)).thenReturn(expectedResult)

        val actualResult = userService.getUserById(userId)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `getUserById throws NoSuchElementException when user not found`() {
        val userId = UUID.randomUUID()
        whenever(userFinder.findUserById(userId)).thenThrow(NoSuchElementException::class.java)

        assertThrows<NoSuchElementException> {
            userService.getUserById(userId)
        }
    }

    @Test
    fun `createUser returns created user`() {
        val userFormDto = getMockUserFormDto()
        val expectedResult = getMockUserDto()
        whenever(userWriter.createUser(userFormDto)).thenReturn(expectedResult)

        val actualResult = userService.createUser(userFormDto)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `createUser throws ConflictException when user already exists`() {
        val userFormDto = getMockUserFormDto()
        whenever(userWriter.createUser(userFormDto)).thenThrow(ConflictException::class.java)

        assertThrows<ConflictException> {
            userService.createUser(userFormDto)
        }
    }
}