package com.info.applicationcore.exception

class ErrorResponse(
    var message: String?,
    var status: Int?,
    var code: String?,
) {
    constructor():this(
        message = null,
        status = null,
        code = null
    )

    constructor(
        message: String? = null,
        code: ErrorCode
    ) : this(
        message = message ?: code.message,
        status = code.status,
        code = code.code
    )
}


