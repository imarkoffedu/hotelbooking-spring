package com.imarkoff.hotelbooking.api.auth.application

import com.imarkoff.hotelbooking.api.auth.presentation.dtos.AuthenticationRequest
import com.imarkoff.hotelbooking.api.auth.presentation.dtos.AuthenticationResponse
import com.imarkoff.hotelbooking.api.user.persistence.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AuthenticationService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.login,
                request.password
            )
        )

        val user = userRepository.findByEmail(request.login)
            ?: throw UsernameNotFoundException("User not found")

        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(token = jwtToken)
    }
}