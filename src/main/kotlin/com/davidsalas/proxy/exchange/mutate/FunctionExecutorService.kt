package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class FunctionExecutorService(
        val mapper: ObjectMapper
) {

    enum class InFunction(val value: String) {
        DEFAULT("DEFAULT"),
        ADD_FILTER_TEAM_PARAMS("ADD_FILTER_TEAM_PARAMS");

        companion object {
            private val map = values().associateBy(InFunction::value)
            @JvmStatic
            fun from(value: String) = map[value] ?: throw Exception("No existe una funcion con ese nombre: $value")
        }
    }

    enum class OutFunction(val value: String) {
        DEFAULT("DEFAULT"),
        FILTER_TEAM_BY_COUNTRY("FILTER_TEAM_BY_COUNTRY");

        companion object {
            private val map = values().associateBy(OutFunction::value)
            @JvmStatic
            fun from(value: String) = map[value] ?: throw Exception("No existe una funcion con ese nombre: $value")
        }
    }

    fun executeInFunction(function: String, payload: JsonNode?, params: MutableMap<String, String>,
                          headers: Map<String, String>): JsonNode? {
        return when (enumValueOf<InFunction>(function)) {
            InFunction.DEFAULT -> defaultFunction(payload)
            InFunction.ADD_FILTER_TEAM_PARAMS -> {
                addCountryFilterToParamsMap(params, headers)
                defaultFunction(payload)
            }
        }
    }

    fun executeOutFunction(function: String, payload: JsonNode, params: Map<String, String>): JsonNode? {
        return when (enumValueOf<OutFunction>(function)) {
            OutFunction.DEFAULT -> defaultFunction(payload)
            OutFunction.FILTER_TEAM_BY_COUNTRY -> filterTeamsByCountry(payload, params, mapper)
        }
    }
}