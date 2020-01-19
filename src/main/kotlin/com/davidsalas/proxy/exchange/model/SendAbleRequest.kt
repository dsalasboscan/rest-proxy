package com.davidsalas.proxy.exchange.model

import com.davidsalas.proxy.exchange.SendResolver
import com.davidsalas.proxy.exchange.info.ProxiedServiceInfo
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange
import org.springframework.http.ResponseEntity

data class SendAbleRequest(
        val proxyExchange: ProxyExchange<JsonNode>,
        val proxiedServiceInfo: ProxiedServiceInfo,
        val params: Map<String, String>
) {
    fun send(sendResolver: SendResolver): ResponseEntity<JsonNode> {
        val response = sendResolver.resolveExchange(proxyExchange, proxiedServiceInfo.httpMethod)
        return ResponseEntity(sendResolver
                .resolveResponseMutation(proxiedServiceInfo.outFunction, response.payload, params), response.httpStatus)
    }
}