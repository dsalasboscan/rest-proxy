package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode

interface InFunction {

    fun invoke(payload: JsonNode?, params: MutableMap<String, String>, headers: Map<String, String>): JsonNode?

}