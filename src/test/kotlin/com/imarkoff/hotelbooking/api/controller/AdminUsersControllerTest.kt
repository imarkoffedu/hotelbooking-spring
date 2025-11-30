package com.imarkoff.hotelbooking.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.imarkoff.hotelbooking.api.controller.api.AdminUsersController
import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.exception.ConflictException
import com.imarkoff.hotelbooking.api.service.userservice.UserService
import com.imarkoff.hotelbooking.api.service.userservice.getMockUserDto
import com.imarkoff.hotelbooking.api.service.userservice.getMockUserFormDto
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test
import com.imarkoff.hotelbooking.api.configuration.SecurityConfig

@WebMvcTest(AdminUsersController::class)
@Import(SecurityConfig::class)
class AdminUsersControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var userService: UserService

    @Test
    @WithMockUser(roles = ["BOOKER"])
    fun `getAllUsers throws forbidden for non-admin users`() {
        mockMvc.perform(get("/admin/users/")
            .with(csrf())
        )
            .andExpect(status().isForbidden)
    }

    @Test
    fun `getAllUsers throws unauthorized for unauthenticated users`() {
        mockMvc.perform(get("/admin/users/")
            .with(csrf())
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `getAllUsers returns all users`() {
        val users = listOf(getMockUserDto(), getMockUserDto())
        whenever(userService.getAllUsers()).thenReturn(users)

        mockMvc.perform(get("/admin/users/")
            .with(csrf())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(users[0].id))
            .andExpect(jsonPath("$[1].id").value(users[1].id))
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `getAllUsers returns empty list when no users exist`() {
        val users = emptyList<UserDto>()
        whenever(userService.getAllUsers()).thenReturn(users)

        mockMvc.perform(get("/admin/users/")
            .with(csrf())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `createUser returns created user`() {
        val userForm = getMockUserFormDto()
        val expectedUser = getMockUserDto()
        whenever(userService.createUser(userForm))
            .thenReturn(expectedUser)

        mockMvc.perform(post("/admin/users/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userForm))
            .with(csrf())
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(expectedUser.id))
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `createUser returns 409 if user with the same email already exists`() {
        val userForm = getMockUserFormDto()
        whenever(userService.createUser(userForm))
            .thenThrow(ConflictException("User with the same email already exists"))

        mockMvc.perform(post("/admin/users/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userForm))
            .with(csrf())
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.status").value(409))
            .andExpect(jsonPath("$.message").value("User with the same email already exists"))
    }
}