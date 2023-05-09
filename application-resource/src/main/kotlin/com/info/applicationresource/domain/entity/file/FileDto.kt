package com.info.applicationresource.domain.entity.file

class FileDto(
    fileId: String,
    fileUrl: String,
    fileType: FileType,
    extension: String,
    fileName: String
) {
    val fileId: String = fileId
    var fileUrl: String = fileUrl
    val fileType: FileType = fileType
    val extension: String = extension
    val fileName: String = fileName
    var authenticatedUri: String? = null
        protected set

}
