package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service

@Service
class InFunctionDefault : InFunction {
    override fun invoke(payload: JsonNode?, params: MutableMap<String, String>, headers: Map<String, String>): JsonNode? {
        return payload
    }
}