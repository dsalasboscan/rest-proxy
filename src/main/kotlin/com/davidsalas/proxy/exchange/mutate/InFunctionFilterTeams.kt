package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service

@Service
class InFunctionFilterTeams : InFunction {

    override fun invoke(payload: JsonNode?, params: MutableMap<String, String>, headers: Map<String, String>): JsonNode? {
        val countryName = headers[ParamKeys.COUNTRY_NAME.value.toLowerCase()] ?: ""
        params[ParamKeys.COUNTRY_NAME.name] = countryName
        return payload
    }
}