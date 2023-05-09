package com.info.applicationresource.domain.entity.file

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@DiscriminatorValue("user_profile_photo")
@OnDelete(action = OnDeleteAction.CASCADE)
class UserProfilePhoto(
    dto: FileDto,
    userEmail: String
): File(
    dto.fileId,
    dto.fileUrl,
    dto.fileType,
    dto.extension,
    dto.fileName
) {
    @Column(name = "user_email", nullable = false)
    var userEmail: String = userEmail

}


