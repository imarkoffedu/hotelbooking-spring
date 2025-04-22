package com.imarkoff.propertyagency.propertyagency.repository

import com.imarkoff.propertyagency.propertyagency.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}
