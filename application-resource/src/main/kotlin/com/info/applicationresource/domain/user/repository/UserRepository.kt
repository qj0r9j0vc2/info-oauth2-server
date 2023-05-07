package com.info.applicationresource.domain.user.repository

import com.info.applicationresource.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
}
