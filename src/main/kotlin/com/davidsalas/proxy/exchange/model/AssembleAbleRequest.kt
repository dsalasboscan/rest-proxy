package com.davidsalas.proxy.exchange.model

import com.davidsalas.proxy.exchange.AssembleResolver
import com.davidsalas.proxy.exchange.mutate.FunctionExecutorService
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
        proxyExchange.body(assembleResolver.resolveRequestMutation(proxiedServiceInfo.inFunction, body, params, headers))
        assembleResolver.resolveSetSensitiveHeaders(proxyExchange, headers)

        return SendAbleRequest(proxyExchange, proxiedServiceInfo, params)
    }

    private fun validateFunctionExistence(inFunction: String, outFunction: String) {
        FunctionExecutorService.InFunction.from(inFunction)
        FunctionExecutorService.OutFunction.from(outFunction)
    }
}
