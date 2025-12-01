package com.imarkoff.hotelbooking.api.user.domain

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.security.Principal
import java.util.UUID

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id")
    var id: UUID,

    @get:JvmName("getDisplayName")
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @get:JvmName("getDisplayPassword")
    var password: String? = null,
    var accountLocked: Boolean = false,
    var enabled: Boolean = false,

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: MutableList<Role>? = null
) : UserDetails, Principal {
    override fun getName(): String {
        return email
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.roles?.stream()
            ?.map { role -> SimpleGrantedAuthority(role.name) }
            ?.toList() ?: mutableListOf()
    }

    override fun getPassword(): String? {
        return this.password
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return !accountLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}