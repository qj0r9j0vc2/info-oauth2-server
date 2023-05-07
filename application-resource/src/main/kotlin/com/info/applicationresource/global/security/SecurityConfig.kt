package com.info.applicationresource.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .formLogin().disable()
            .authorizeHttpRequests{ a ->
//                a.requestMatchers("/user/**").hasAnyAuthority("SCOPE_STUDENT","SCOPE_STUDENT")
                a.requestMatchers("/user/**").hasAnyAuthority("test-scope")
                a.anyRequest().hasAuthority("SCOPE_TEACHER")
                .and()
                .oauth2ResourceServer()
                .jwt()
            }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


}
