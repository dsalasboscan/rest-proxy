package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service

@Service
class FunctionExecutorService(
        val inFunctionDefault: InFunction,
        val inFunctionFilterTeams: InFunction,
        val outFunctionDefault: OutFunction,
        val outFunctionFilterTeams: OutFunction
) {

    fun executeInFunction(function: InAvailableFunction, payload: JsonNode?, params: MutableMap<String, String>, headers: Map<String, String>): JsonNode? {
        return when (function) {
            InAvailableFunction.DEFAULT -> inFunctionDefault.invoke(payload, params, headers)
            InAvailableFunction.IN_FILTER_TEAMS -> inFunctionFilterTeams.invoke(payload, params, headers)
        }
    }

    fun executeOutFunction(function: OutAvailableFunction, payload: JsonNode?, params: Map<String, String>): JsonNode? {
        if (payload == null) return null
        return when (function) {
            OutAvailableFunction.DEFAULT -> outFunctionDefault.invoke(payload, params)
            OutAvailableFunction.OUT_FILTER_TEAMS -> outFunctionFilterTeams.invoke(payload, params)
        }
    }
}