package com.imarkoff.hotelbooking.api.controller

import com.imarkoff.hotelbooking.api.user.presentation.UsersController
import com.imarkoff.hotelbooking.api.user.application.UserService
import com.imarkoff.hotelbooking.api.service.userservice.getMockUserDto
import com.imarkoff.hotelbooking.api.shared.`typealias`.toUUID
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.Test

@WebMvcTest(UsersController::class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var userService: UserService

    @Test
    @WithMockUser(roles = ["ADMIN", "BOOKER"])
    fun `getUserById returns user for authenticated users`() {
        val user = getMockUserDto()
        whenever(userService.getUserById(user.id.toUUID()))
            .thenReturn(user)

        mockMvc.perform(get("/users/${user.id}")
            .with(csrf())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(user.id))
    }

    @Test
    fun `getUserById returns user if it exists`() {
        val user = getMockUserDto()
        whenever(userService.getUserById(user.id.toUUID()))
            .thenReturn(user)

        mockMvc.perform(get("/users/${user.id}")
            .with(csrf())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(user.id))
    }

    @Test
    fun `getUserById returns 404 if user does not exist`() {
        val userId = UUID.randomUUID()
        whenever(userService.getUserById(userId))
            .thenThrow(NoSuchElementException())

        mockMvc.perform(get("/users/$userId"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("User not found"))
    }
}