package com.davidsalas.proxy.exchange

import com.davidsalas.proxy.exchange.info.ProxiedServiceInfo
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange

interface AssembleResolver {

    fun resolveGetServiceInformation(serviceId: String): ProxiedServiceInfo

    fun resolveBuildUri(uri: String, params: Map<String, String>?): String

    fun resolveSetSensitiveHeaders(proxyExchange: ProxyExchange<JsonNode>, headers: Map<String, String>)

    fun resolveRequestMutation(function: String, payload: JsonNode?, params: MutableMap<String, String>,
                               headers: Map<String, String>): JsonNode?
}
