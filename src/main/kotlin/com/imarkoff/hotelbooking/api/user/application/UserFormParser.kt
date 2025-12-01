package com.imarkoff.hotelbooking.api.user.application

import com.imarkoff.hotelbooking.api.user.presentation.dtos.UserFormDto
import com.imarkoff.hotelbooking.api.user.domain.User
import com.imarkoff.hotelbooking.api.user.domain.Role
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserFormParser {
    private lateinit var passwordEncoder: PasswordEncoder

    fun parse(userForm: UserFormDto, userId: UUID? = null): User {
        val id = userId ?: UUID.randomUUID()
        return User(
            id = id,
            name = userForm.name,
            email = userForm.email,
            password = passwordEncoder.encode(userForm.password),
            accountLocked = false,
            enabled = true,
            roles = mutableListOf(Role.USER)
        )
    }
}