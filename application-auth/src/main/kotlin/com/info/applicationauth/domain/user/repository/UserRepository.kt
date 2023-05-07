package com.info.applicationauth.domain.user.repository

import com.info.applicationauth.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<User, String> {

    fun findByEmail(email: String): Optional<User>
}
