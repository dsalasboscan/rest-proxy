package com.davidsalas.proxy.exchange.service

import com.davidsalas.proxy.exchange.model.Response
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cloud.gateway.mvc.ProxyExchange
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException


@Service
class ExchangeService(
        private val mapper: ObjectMapper = jacksonObjectMapper()
) {

    fun exchange(proxyExchange: ProxyExchange<JsonNode>, httpMethod: String): Response {
        return try {
            when (httpMethod) {
                RequestMethod.GET.name -> toResponse(proxyExchange.get())
                RequestMethod.POST.name -> toResponse(proxyExchange.post())
                RequestMethod.PUT.name -> toResponse(proxyExchange.put())
                RequestMethod.DELETE.name -> toResponse(proxyExchange.delete())
                RequestMethod.HEAD.name -> toResponse(proxyExchange.head())
                RequestMethod.OPTIONS.name -> toResponse(proxyExchange.options())
                RequestMethod.PATCH.name -> toResponse(proxyExchange.patch())
                else -> throw Exception(httpMethod)
            }
        } catch (e: Exception) {
            when (e) {
                is HttpServerErrorException, is HttpClientErrorException -> {
                    throw Exception()
                }
                else -> throw e
            }
        }
    }

    private fun toResponse(response: ResponseEntity<JsonNode>): Response {
        return Response(
                response.body ?: mapper.createObjectNode(),
                response.statusCode
        )
    }
}