package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.dto.UserFormDto
import com.imarkoff.hotelbooking.api.exception.ConflictException
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class UserWriterTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userFinder: UserFinder

    @Mock
    private lateinit var userFormParser: UserFormParser

    @Mock
    private lateinit var dtoFactory: DtoFactory

    @InjectMocks
    private lateinit var userWriter: UserWriter

    @Test
    fun `createUser saves user and returns UserDto`() {
        val (userFormDto, user, userDto) = mockCorrectCreationData()

        val result = userWriter.createUser(userFormDto)

        verify(userRepository).save(user)
        assertEquals(userDto, result)
    }

    private fun mockCorrectCreationData(): Triple<UserFormDto, User, UserDto> {
        val userFormDto = getMockUserFormDto()
        val user = getMockUser()
        val userDto = getMockUserDto()
        whenever(userFinder.findUserByEmail(userFormDto.email))
            .thenThrow(NoSuchElementException::class.java)
        whenever(userFormParser.parse(userFormDto)).thenReturn(user)
        whenever(dtoFactory.from(user)).thenReturn(userDto)

        return Triple(userFormDto, user, userDto)
    }

    @Test
    fun `createUser executes if user with email does not exist`() {
        val (userFormDto, _, userDto) = mockCorrectCreationData()

        userWriter.createUser(userFormDto)

        assertEquals(userFormDto.email, userDto.email)
    }

    @Test
    fun `createUser throws ConflictException if user with email exists`() {
        val userFormDto = getMockUserFormDto()
        val user = getMockUser()
        whenever(userFinder.findUserByEmail(userFormDto.email)).thenReturn(user)

        assertThrows<ConflictException> {
            userWriter.createUser(userFormDto)
        }
    }
}