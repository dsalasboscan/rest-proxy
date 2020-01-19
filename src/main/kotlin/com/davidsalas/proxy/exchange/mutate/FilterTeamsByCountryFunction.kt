package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

private data class Team(
        val id: Int,
        val name: String,
        val country: String,
        val founded: Int,
        val venue: Venue
) {
    private data class Venue(
            val name: String,
            val city: String,
            val capacity: Int
    )
}

fun filterTeamsByCountry(payload: JsonNode, params: Map<String, String>, mapper: ObjectMapper): JsonNode {
    val response = mapper.createArrayNode()
    val country = params[ParamKeys.COUNTRY_NAME.name]
    val teams: List<Team> = mapper.readValue(payload.toPrettyString())
    teams.filter { it.country == country }.map { response.add(mapper.readTree(mapper.writeValueAsString(it))) }
    return response
}