package com.info.applicationresource.domain.presentation.dto.response

import com.info.applicationresource.domain.entity.user.Role

class TeacherResponse(
    name: String,
    email: String,
    profileUrl: String?,
): UserResponse(
    email,
    name,
    Role.TEACHER,
    profileUrl
) {
}
