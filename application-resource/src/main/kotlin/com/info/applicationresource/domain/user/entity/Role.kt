package com.info.applicationresource.domain.user.entity

enum class Role(
    val level: Int,
    val mean: String
) {
    BLOCK(0, "ROLE_BLOCK"),
    STUDENT(1, "ROLE_STUDENT"),
    CONTACTOR(2, "ROLE_CONTACTOR"),
    TEACHER(3, "ROLE_TEACHER"),
    SYSTEM(4, "ROLE_SYSTEM")

}

