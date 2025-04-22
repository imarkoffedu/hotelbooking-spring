package com.imarkoff.hotelbooking.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.imarkoff.hotelbooking.api.controller.api.UserController
import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.exception.ConflictException
import com.imarkoff.hotelbooking.api.service.userservice.UserService
import com.imarkoff.hotelbooking.api.service.userservice.getMockUserDto
import com.imarkoff.hotelbooking.api.service.userservice.getMockUserFormDto
import com.imarkoff.hotelbooking.api.type.`typealias`.toUUID
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.Test

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var userService: UserService

    @Test
    fun `getAllUsers returns all users`() {
        val users = listOf(getMockUserDto(), getMockUserDto())
        whenever(userService.getAllUsers()).thenReturn(users)

        mockMvc.perform(get("/users/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(users[0].id))
            .andExpect(jsonPath("$[1].id").value(users[1].id))
    }

    @Test
    fun `getAllUsers returns empty list when no users exist`() {
        val users = emptyList<UserDto>()
        whenever(userService.getAllUsers()).thenReturn(users)

        mockMvc.perform(get("/users/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `getUserById returns user if it exists`() {
        val user = getMockUserDto()
        whenever(userService.getUserById(user.id.toUUID()))
            .thenReturn(user)

        mockMvc.perform(get("/users/${user.id}"))
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

    @Test
    fun `createUser returns created user`() {
        val userForm = getMockUserFormDto()
        val expectedUser = getMockUserDto()
        whenever(userService.createUser(userForm))
            .thenReturn(expectedUser)

        mockMvc.perform(post("/users/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userForm))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(expectedUser.id))
    }

    @Test
    fun `createUser returns 409 if user with the same email already exists`() {
        val userForm = getMockUserFormDto()
        whenever(userService.createUser(userForm))
            .thenThrow(ConflictException("User with the same email already exists"))

        mockMvc.perform(post("/users/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userForm))
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.status").value(409))
            .andExpect(jsonPath("$.message").value("User with the same email already exists"))
    }
}