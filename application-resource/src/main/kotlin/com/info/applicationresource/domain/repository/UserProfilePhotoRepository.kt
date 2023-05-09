package com.info.applicationresource.domain.repository

import com.info.applicationresource.domain.entity.file.UserProfilePhoto
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserProfilePhotoRepository: JpaRepository<UserProfilePhoto, String> {

    fun findByUserEmail(userEmail: String): Optional<UserProfilePhoto>
}
