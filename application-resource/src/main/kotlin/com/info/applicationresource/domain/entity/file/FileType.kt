package com.info.applicationresource.domain.entity.file

enum class FileType(val value: Int) {
    IMAGE(0),
    DOCS(1),
    UNKNOWN(2);


    companion object {
        fun fromInt(value: Int) = FileType.values().first { it.value == value }
    }
}
