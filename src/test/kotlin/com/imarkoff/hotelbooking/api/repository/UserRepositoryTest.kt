package com.imarkoff.hotelbooking.api.repository

import com.imarkoff.hotelbooking.api.service.userservice.getMockUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `findByEmail finds the user based on email`() {
        val expectedUser = getMockUser()
        userRepository.save(expectedUser)

        val actualUser = userRepository.findByEmail(expectedUser.email)!!

        assertEquals(expectedUser.email, actualUser.email)
    }

    @Test
    fun `findByEmail returns null if the user with specified email does not exist`() {
        val user = getMockUser()
        userRepository.save(user)
        val email = "not.existing@mail.com"

        val result = userRepository.findByEmail(email)

        assertEquals(null, result)
    }
}