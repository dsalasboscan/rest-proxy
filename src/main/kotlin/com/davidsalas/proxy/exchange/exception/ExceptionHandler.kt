package com.davidsalas.proxy.exchange.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }

    @ExceptionHandler(HttpException::class)
    fun handle(e: HttpException): ResponseEntity<ErrorMessage> {
        logger.error("HTTP Exception: status = ${e.status}, body = ${e.body}")
        return ResponseEntity(ErrorMessage(e.body), e.status)
    }
}