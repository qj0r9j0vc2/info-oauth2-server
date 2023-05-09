package com.info.applicationauth.global.exception

import com.info.applicationcore.exception.CommonException
import com.info.applicationcore.exception.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(CommonException::class)
    protected fun commonExceptionHandler(e: CommonException): ResponseEntity<Any>? {
        log.warn("CommonException occurred: ${e.message?:e.errorCode.message}")
        return ResponseEntity.status(e.errorCode.status).body(
            ErrorResponse(
            e.message?:e.errorCode.message,
                e.errorCode
            )
        )
    }

}
