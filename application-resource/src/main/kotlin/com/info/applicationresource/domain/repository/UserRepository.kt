package com.info.applicationresource.domain.repository

import com.info.applicationresource.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
}
