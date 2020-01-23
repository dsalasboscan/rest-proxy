package com.davidsalas.proxy.exchange.model

import com.davidsalas.proxy.exchange.AssembleResolver
import com.davidsalas.proxy.exchange.mutate.InAvailableFunction
import com.davidsalas.proxy.exchange.mutate.OutAvailableFunction
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange

data class AssembleAbleRequest(
        val proxyExchange: ProxyExchange<JsonNode>,
        val serviceId: String,
        val body: JsonNode?,
        val urlParams: Map<String, String>?,
        val headers: Map<String, String>
) {

    fun assemble(assembleResolver: AssembleResolver): SendAbleRequest {
        val proxiedServiceInfo = assembleResolver.resolveGetServiceInformation(serviceId)
        val params = mutableMapOf<String, String>()

        validateFunctionExistence(proxiedServiceInfo.inFunction, proxiedServiceInfo.outFunction)

        proxyExchange.uri(assembleResolver.resolveBuildUri(proxiedServiceInfo.uri, urlParams))
        proxyExchange.body(assembleResolver.resolveRequestMutation(InAvailableFunction.from(proxiedServiceInfo.inFunction), body, params, headers))

        removeSensitiveHeaders(proxyExchange, headers)

        return SendAbleRequest(proxyExchange, proxiedServiceInfo, params)
    }

    private fun validateFunctionExistence(inFunction: String, outFunction: String) {
        InAvailableFunction.from(inFunction)
        OutAvailableFunction.from(outFunction)
    }

    private fun removeSensitiveHeaders(proxyExchange: ProxyExchange<JsonNode>, headers: Map<String, String>) {
        val proxyHeaderBase = "proxy_param_header_"
        headers.forEach { (key) -> if (key.contains(proxyHeaderBase)) proxyExchange.sensitive(key) }
    }
}
