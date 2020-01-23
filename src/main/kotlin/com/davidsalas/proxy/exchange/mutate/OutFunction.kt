package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode

interface OutFunction {

    fun invoke(payload: JsonNode, params: Map<String, String>): JsonNode
}