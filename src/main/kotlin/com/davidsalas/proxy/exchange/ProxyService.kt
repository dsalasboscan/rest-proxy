package com.davidsalas.proxy.exchange

import com.davidsalas.proxy.exchange.info.ServiceInformation
import com.davidsalas.proxy.exchange.info.ServiceInformationService
import com.davidsalas.proxy.exchange.model.Response
import com.davidsalas.proxy.exchange.mutate.FunctionExecutorService
import com.davidsalas.proxy.exchange.mutate.InAvailableFunction
import com.davidsalas.proxy.exchange.mutate.OutAvailableFunction
import com.davidsalas.proxy.exchange.service.ExchangeService
import com.davidsalas.proxy.exchange.service.UriBuilderService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange
import org.springframework.stereotype.Service

@Service
class ProxyService(
        private val serviceInformationService: ServiceInformationService,
        private val exchangeService: ExchangeService,
        private val uriBuilderService: UriBuilderService,
        private val functionExecutorService: FunctionExecutorService
) : OrchestratorService() {

    override fun resolveGetServiceInformation(serviceId: String): ServiceInformation =
            serviceInformationService.getServiceInfoData(serviceId)

    override fun resolveBuildUri(uri: String, params: Map<String, String>?): String =
            uriBuilderService.build(uri, params)

    override fun resolveRequestMutation(function: InAvailableFunction, payload: JsonNode?, params: MutableMap<String, String>,
                                        headers: Map<String, String>): JsonNode? =
            functionExecutorService.executeInFunction(function, payload, params, headers)

    override fun resolveExchange(proxyExchange: ProxyExchange<JsonNode>, httpMethod: String): Response =
            exchangeService.exchange(proxyExchange, httpMethod)

    override fun resolveResponseMutation(function: OutAvailableFunction, payload: JsonNode?, params: Map<String, String>): JsonNode? =
            functionExecutorService.executeOutFunction(function, payload, params)
}