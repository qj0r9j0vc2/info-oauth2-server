package com.info.applicationresource.domain.business

import com.info.applicationcore.exception.CommonException
import com.info.applicationcore.exception.ErrorCode
import com.info.applicationresource.domain.entity.user.Student
import com.info.applicationresource.domain.entity.user.Teacher
import com.info.applicationresource.domain.presentation.dto.response.UserResponse
import com.info.applicationresource.domain.repository.UserProfilePhotoRepository
import com.info.applicationresource.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ResourceServiceImpl(
    private val userProfilePhotoRepository: UserProfilePhotoRepository,
    private val userRepository: UserRepository
): ResourceService {
    override fun loadUser(claims: Map<String, Any>): UserResponse {
        val user = userRepository.findById(claims["sub"].toString()).orElse(null)
            ?: throw CommonException(errorCode = ErrorCode.DATA_NOT_FOUND_ERROR)
        return if (user is Student) {
            user.toStudentResponse(
                userProfilePhotoRepository.findByUserEmail(claims["sub"].toString()).orElse(null)
                    ?.fileUrl
            )
        } else if (user is Teacher) {
            user.toTeacherResponse(
                userProfilePhotoRepository.findByUserEmail(claims["sub"].toString()).orElse(null)
                    ?.fileUrl
            )
        } else throw CommonException(errorCode = ErrorCode.NOT_SUPPORTED_ERROR)
    }

}
