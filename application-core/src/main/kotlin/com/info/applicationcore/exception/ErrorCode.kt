package com.info.applicationcore.exception

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    //Server Error
    INTERNAL_SERVER_ERROR(500, "E01", "Internal error occurred"),


    //Client Error
    INVALID_REQUEST_ERROR(400, "E02", "Invalid request error"),
    NOT_MATCH_ERROR(400, "E04", "Not matched with comparer"),

    DATA_NOT_FOUND_ERROR(404, "E03", "Data not found")


}
