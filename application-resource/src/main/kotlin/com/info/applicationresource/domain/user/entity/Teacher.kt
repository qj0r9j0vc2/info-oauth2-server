package com.info.applicationresource.domain.user.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@Entity
@DiscriminatorValue("teacher")
@OnDelete(action = OnDeleteAction.CASCADE)
class Teacher(
    name: String,
    email: String,
    password: String
): User(
    name,
    email,
    password,
    Role.TEACHER,
    null
) {

}
