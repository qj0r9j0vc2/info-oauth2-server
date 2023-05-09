package com.info.applicationresource.domain.entity.user

import com.info.applicationresource.domain.presentation.dto.response.StudentResponse
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import jakarta.persistence.*


@Entity
@DiscriminatorValue("student")
@OnDelete(action = OnDeleteAction.CASCADE)
class Student(
    studentKey: String,
    name: String,
    email: String,
    password: String,
    githubLink: String?
): User(
    name,
    email,
    password,
    Role.STUDENT,
    null
) {
    val studentKey: String = studentKey

    val entranceYear: Int = LocalDate.now().year - studentKey.substring(0, 1).toInt() + 1


    @Column(name = "github_link")
    var githubLink: String? = githubLink
        protected set

    fun toStudentResponse(profilePhotoLink: String?): StudentResponse{
        return StudentResponse(
            this.email,
            this.name,
            profilePhotoLink,
            this.entranceYear,
            this.studentKey.substring(0, 1).toInt(),
            this.studentKey.substring(1, 2).toInt(),
            this.studentKey.substring(2, 4).toInt(),
            this.githubLink
        )
    }



}
