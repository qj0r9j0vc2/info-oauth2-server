package com.info.applicationcore.exception

open class CommonException(
    message: String? = null,
    val errorCode: ErrorCode
): java.lang.RuntimeException() {
    override val message: String? = message?.let { it }
}
