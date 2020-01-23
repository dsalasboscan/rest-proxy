package com.davidsalas.proxy.exchange.model

import com.davidsalas.proxy.exchange.SendResolver
import com.davidsalas.proxy.exchange.info.ServiceInformation
import com.davidsalas.proxy.exchange.mutate.OutAvailableFunction
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange
import org.springframework.http.ResponseEntity

data class SendAbleRequest(
        val proxyExchange: ProxyExchange<JsonNode>,
        val serviceInformation: ServiceInformation,
        val params: Map<String, String>
) {
    fun send(sendResolver: SendResolver): ResponseEntity<JsonNode> {
        val response = sendResolver.resolveExchange(proxyExchange, serviceInformation.httpMethod)

        val responseAsJsonNode = sendResolver
                .resolveResponseMutation(OutAvailableFunction.from(serviceInformation.outFunction), response.payload, params)

        return ResponseEntity(responseAsJsonNode, response.httpStatus)
    }
}