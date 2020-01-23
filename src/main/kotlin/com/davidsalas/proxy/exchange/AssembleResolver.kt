package com.davidsalas.proxy.exchange

import com.davidsalas.proxy.exchange.info.ServiceInformation
import com.davidsalas.proxy.exchange.mutate.InAvailableFunction
import com.fasterxml.jackson.databind.JsonNode

interface AssembleResolver {

    fun resolveGetServiceInformation(serviceId: String): ServiceInformation

    fun resolveBuildUri(uri: String, params: Map<String, String>?): String

    fun resolveRequestMutation(function: InAvailableFunction, payload: JsonNode?, params: MutableMap<String, String>,
                               headers: Map<String, String>): JsonNode?
}
