package com.info.applicationauth.global.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class CustomAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {


    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val username: String = authentication.name
        val password = authentication.credentials as String
        val accountContext = userDetailsService.loadUserByUsername(username)?:throw UsernameNotFoundException(username)
        if (!passwordEncoder.matches(password, accountContext.password)) {
            throw BadCredentialsException("BadCredentialsException'")
        }
        return UsernamePasswordAuthenticationToken(
            accountContext.username,
            null,
            accountContext.authorities
        )
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.let { UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(it) }
            ?: false
    }
}
