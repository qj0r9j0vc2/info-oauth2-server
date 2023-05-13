package com.info.applicationauth.global.security

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException


@Component
class AuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        var errorMessage = "Invalid Username or Password"
        if (exception is BadCredentialsException) {
            errorMessage = "Invalid Username or Password"
        } else if (exception is InsufficientAuthenticationException) {
            errorMessage = "Invalid Secret key"
        }
        log.warn("$errorMessage")
        setDefaultFailureUrl("/info-login-fail?error=true&exception=$errorMessage")
        super.onAuthenticationFailure(request, response, exception)
    }
}
