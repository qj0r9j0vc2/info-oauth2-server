package com.info.applicationresource.domain.entity.file

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where


@Where(clause = "file_is_deleted = false")
@SQLDelete(sql = "UPDATE `file` SET file_is_deleted = true where file_id = ?")
@Table(name = "file")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_type", length = 50)
@Entity
abstract class File(
    id: String,
    fileUrl: String,
    fileType: FileType,
    extension: String,
    fileName: String
) {

    @Id
    @Column(name = "file_id", nullable = false)
    val id: String = id

    @Column(name = "file_url", nullable = false, length = 3000)
    var fileUrl: String = fileUrl
        protected set

    @Column(name = "file_name", nullable = false)
    val fileName: String = fileName

    @Column(name = "file_content_type", nullable = false)
    @Enumerated
    var fileContentType: FileType = fileType
        protected set

    @Column(name = "file_extension", nullable = false)
    var extension: String = extension
        protected set

    @Column(name = "file_is_deleted")
    var isDeleted: Boolean = false
        protected set

    var isNew: Boolean = true
        protected set

}
