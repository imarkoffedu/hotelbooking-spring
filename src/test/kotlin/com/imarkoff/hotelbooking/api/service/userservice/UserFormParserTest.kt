package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class UserFormParserTest {
    @InjectMocks
    private lateinit var userFormParser: UserFormParser

    @Test
    fun `parse should convert UserFormDto to User`() {
        val userFormDto = getMockUserFormDto()
        val userId = UUID.randomUUID()
        val expectedUser = User(
            id = userId,
            name = userFormDto.name,
            email = userFormDto.email
        )

        val actualUser = userFormParser.parse(userFormDto, userId)

        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun `parse should generate a new UUID if not provided`() {
        val userFormDto = getMockUserFormDto()

        val actualUser = userFormParser.parse(userFormDto)

        assertEquals(userFormDto.name, actualUser.name)
        assertEquals(userFormDto.email, actualUser.email)
        assertTrue(actualUser.id.toString().isNotEmpty())
        assertTrue(actualUser.id.toString().isNotBlank())
        assertTrue(actualUser.id != UUID(0, 0))
    }
}