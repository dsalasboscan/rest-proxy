package com.davidsalas.proxy.exchange.mutate

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service

@Service
class OutFunctionFilterTeams(val mapper: ObjectMapper) : OutFunction {

    companion object {
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
    }

    override fun invoke(payload: JsonNode, params: Map<String, String>): JsonNode {
        val response = mapper.createArrayNode()

        val country = params[ParamKeys.COUNTRY_NAME.name]

        val teams: List<Team> = mapper.readValue(payload.toPrettyString())

        if (country.isNullOrEmpty()) return mapper.readTree(mapper.writeValueAsString(teams))

        teams.filter { it.country.toLowerCase() == country.toLowerCase() }
                .map { response.add(mapper.readTree(mapper.writeValueAsString(it))) }

        return response
    }
}