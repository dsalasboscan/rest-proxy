package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service

@Service
class OutFunctionDefault : OutFunction {
    override fun invoke(payload: JsonNode, params: Map<String, String>): JsonNode {
        return payload
    }
}