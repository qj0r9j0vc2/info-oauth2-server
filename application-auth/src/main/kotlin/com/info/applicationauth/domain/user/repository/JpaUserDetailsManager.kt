package com.info.applicationauth.domain.user.repository

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class JpaUserDetailsManager(
    private val userRepository: UserRepository
): UserDetailsManager {

    override fun loadUserByUsername(email: String?): UserDetails? {
        email?.let {
            val user = userRepository.findByEmail(it).orElse(null)
            val authorities = HashSet<GrantedAuthority>()
            user.authorities.map {
                authorities.add(SimpleGrantedAuthority(it.authority))
            }
            return User(
                user.email,
                user.password,
                user.isEnabled,
                user.isAccountNonExpired,
                user.isCredentialsNonExpired,
                user.isAccountNonLocked,
                authorities
            )
        }?: return null
    }

    override fun createUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: UserDetails?) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(username: String?) {
        TODO("Not yet implemented")
    }

    override fun changePassword(oldPassword: String?, newPassword: String?) {
        TODO("Not yet implemented")
    }

    override fun userExists(email: String): Boolean {
        return userRepository.existsById(email)
    }


}
