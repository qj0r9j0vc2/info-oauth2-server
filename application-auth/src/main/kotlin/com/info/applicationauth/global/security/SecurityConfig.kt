package com.info.applicationauth.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig(
) {

    @Bean
    @Order(2)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .formLogin(withDefaults())
            .cors().disable()
            .csrf().disable()
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/client/**", "/error/**").permitAll()
                    .anyRequest().authenticated() }
            .exceptionHandling().disable()
            .build()
    }

    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/oauth2/client/**"
            )
        }
    }

}
