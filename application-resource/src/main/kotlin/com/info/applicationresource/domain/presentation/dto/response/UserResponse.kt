package com.info.applicationresource.domain.presentation.dto.response

import com.info.applicationresource.domain.entity.user.Role

open class UserResponse(
    val email: String,
    val name: String,
    val role: Role,
    val profileUrl: String?
)
