package com.imarkoff.hotelbooking.api.core.init

import com.imarkoff.hotelbooking.api.user.domain.User
import com.imarkoff.hotelbooking.api.user.persistence.UserRepository
import com.imarkoff.hotelbooking.api.user.domain.Role
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

/**
 * Creates an initial admin user on application startup when one doesn't exist.
 * Email and password can be configured with properties:
 *  - application.init.admin.email
 *  - application.init.admin.password
 */
@Component
@Profile("!test")
class InitialAdminData(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${application.init.admin.email}")
    private val adminEmail: String,
    @Value("\${application.init.admin.password}")
    private val adminPassword: String
) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(InitialAdminData::class.java)

    override fun run(vararg args: String?) {
        try {
            val existing = userRepository.findByEmail(adminEmail)
            if (existing != null) {
                logger.info("Initial admin already exists with email={}", adminEmail)
                return
            }

            val adminUser = User(
                id = UUID.randomUUID(),
                name = "Administrator",
                email = adminEmail,
                password = passwordEncoder.encode(adminPassword),
                accountLocked = false,
                enabled = true,
                roles = mutableListOf(Role.ADMIN)
            )

            userRepository.save(adminUser)
            logger.info("Created initial admin user: {}", adminEmail)
        } catch (ex: Exception) {
            logger.error("Failed to create initial admin user", ex)
        }
    }
}

