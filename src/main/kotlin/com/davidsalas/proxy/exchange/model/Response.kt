package com.davidsalas.proxy.exchange.model

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpStatus

data class Response(
        val payload: JsonNode?,
        val httpStatus: HttpStatus
)