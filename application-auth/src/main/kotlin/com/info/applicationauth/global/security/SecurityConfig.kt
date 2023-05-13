package com.info.applicationauth.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig(
    private val authenticationFailureHandler: AuthenticationFailureHandler
) {

    @Bean
    @Order(2)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf().disable()
            .cors().disable()
            .formLogin()
                .loginPage("/info-login")
                .loginProcessingUrl("/info-login-proc")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureHandler(AuthenticationFailureHandler())
                .permitAll()
            .and()
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/client/**", "/error/**", "/login/**").permitAll()
                    .anyRequest().authenticated() }
            .build()
    }

    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/oauth2/client/**",
                "/css/**",
                "/img/**",
                "/js/**"
            )
        }
    }

}
