package com.info.applicationresource.domain.presentation

import com.info.applicationcore.exception.CommonException
import com.info.applicationcore.exception.ErrorCode
import com.info.applicationresource.domain.business.ResourceService
import com.info.applicationresource.domain.entity.user.Role
import com.info.applicationresource.domain.presentation.dto.response.ResourceResponse
import com.info.applicationresource.domain.presentation.dto.response.StudentResponse
import com.info.applicationresource.domain.presentation.dto.response.TeacherResponse
import com.info.applicationresource.domain.presentation.dto.response.UserResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserResourceController(
    private val resourceService: ResourceService
) {

    @GetMapping("/user")
    fun getUser(
        @AuthenticationPrincipal jwt: Jwt
    ): ResourceResponse {
        val user = resourceService.loadUser(jwt.claims)

        if (user is StudentResponse) {
            return ResourceResponse(
                user as StudentResponse,
                jwt.claims["scope"] as List<String>
            )
        } else if (user is TeacherResponse) {
            return ResourceResponse(
                user as TeacherResponse,
                jwt.claims["scope"] as List<String>
            )
        }
        throw CommonException(errorCode = ErrorCode.INTERNAL_SERVER_ERROR)
    }

}
