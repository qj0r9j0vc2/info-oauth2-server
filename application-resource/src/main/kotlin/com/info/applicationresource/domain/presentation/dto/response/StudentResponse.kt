package com.info.applicationresource.domain.presentation.dto.response

import com.info.applicationresource.domain.entity.user.Role

class StudentResponse(
    email: String,
    name: String,
    profileUrl: String?,
    val entrancedYear: Int,
    val grade: Int,
    val classNum: Int,
    val studentNum: Int,
    val githubLink: String?
): UserResponse(
    email,
    name,
    Role.STUDENT,
    profileUrl
) {
}
