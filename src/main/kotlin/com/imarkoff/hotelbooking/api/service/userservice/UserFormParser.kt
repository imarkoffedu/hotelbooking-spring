package com.imarkoff.hotelbooking.api.service.userservice

import com.imarkoff.hotelbooking.api.dto.UserFormDto
import com.imarkoff.hotelbooking.api.model.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserFormParser {
    fun parse(userForm: UserFormDto, userId: UUID? = null): User {
        val id = userId ?: UUID.randomUUID()
        return User(
            id = id,
            name = userForm.name,
            email = userForm.email
        )
    }
}