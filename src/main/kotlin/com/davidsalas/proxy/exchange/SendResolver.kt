package com.davidsalas.proxy.exchange

import com.davidsalas.proxy.exchange.model.Response
import com.davidsalas.proxy.exchange.mutate.OutAvailableFunction
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange

interface SendResolver {

    fun resolveExchange(proxyExchange: ProxyExchange<JsonNode>, httpMethod: String): Response

    fun resolveResponseMutation(function: OutAvailableFunction, payload: JsonNode?, params: Map<String, String>): JsonNode?
}