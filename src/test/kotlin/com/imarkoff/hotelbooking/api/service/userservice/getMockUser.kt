package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.dto.UserDto
import com.imarkoff.hotelbooking.api.dto.UserFormDto
import com.imarkoff.hotelbooking.api.model.User
import java.util.*

fun getMockUser() = User(
    name = "John Doe",
    email = "john.doe@example.com",
)

fun getMockUserDto() = UserDto(
    id = UUID.randomUUID().toString(),
    name = "John Doe",
    email = "john.doe@example.com"
)

fun getMockUserFormDto() = UserFormDto(
    name = "John Doe",
    email = "john.doe@example.com",
)