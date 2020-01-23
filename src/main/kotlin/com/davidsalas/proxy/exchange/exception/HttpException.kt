package com.davidsalas.proxy.exchange.exception

import org.springframework.http.HttpStatus

abstract class HttpException(val status: HttpStatus, val body: Any?) : RuntimeException()

open class BadRequestException(body: Any? = null) : HttpException(HttpStatus.BAD_REQUEST, body)

open class NotFoundException(body: Any? = null) : HttpException(HttpStatus.NOT_FOUND, body)

open class AuthenticationFailedException(body: Any? = null) : HttpException(HttpStatus.UNAUTHORIZED, body)

open class InternalServerErrorException(body: Any? = null) : HttpException(HttpStatus.INTERNAL_SERVER_ERROR, body)

open class HttpServerGenericException(httpStatus: HttpStatus, body: Any? = null) : HttpException(httpStatus, body)

open class HttpClientGenericException(httpStatus: HttpStatus, body: Any? = null) : HttpException(httpStatus, body)