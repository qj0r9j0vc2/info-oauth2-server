package com.info.applicationresource.domain.user.entity

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
    githubLink: String?,
    profilePhotoId: Int
): User(
    name,
    email,
    password,
    Role.STUDENT,
    null
) {
    val studentKey: String = studentKey

    val entranceYear: Int = LocalDate.now().year - studentKey.substring(0, 1).toInt() + 1

//    @OneToMany(mappedBy = "student")
//    var hiredStudentList: MutableList<HiredStudent> = ArrayList()
//        protected set
//
//    @OneToMany(mappedBy = "student")
//    var fieldTrainingList: MutableList<FieldTraining> = ArrayList()
//        protected set
//
//    @OneToMany(mappedBy = "student")
//    var applicantList: MutableList<Applicant> = ArrayList()
//        protected set

    @Column(name = "github_link")
    var githubLink: String? = githubLink
        protected set


}
