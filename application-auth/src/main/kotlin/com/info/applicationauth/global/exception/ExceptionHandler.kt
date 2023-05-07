package com.info.applicationauth.global.exception

import com.info.applicationcore.exception.CommonException
import com.info.applicationcore.exception.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(CommonException::class)
    private fun commonExceptionHandler(e: CommonException, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(e, ErrorResponse(
            e.message?:e.errorCode.message,
            e.errorCode
        ), HttpHeaders(), HttpStatus.valueOf(e.errorCode.status), request)
    }


}
