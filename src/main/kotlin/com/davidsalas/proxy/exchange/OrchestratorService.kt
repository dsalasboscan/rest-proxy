package com.davidsalas.proxy.exchange

import com.davidsalas.proxy.exchange.model.AssembleAbleRequest
import com.davidsalas.proxy.exchange.model.SendAbleRequest
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.ResponseEntity
import java.util.*

abstract class OrchestratorService : AssembleResolver, SendResolver {

    open fun execute(request: AssembleAbleRequest): ResponseEntity<JsonNode> =
            assemble(request)
                    .flatMap {
                        send(it)
                    }
                    .orElseThrow { Exception() }

    open fun assemble(request: AssembleAbleRequest) = Optional.of(request.assemble(this))

    open fun send(request: SendAbleRequest) = Optional.of(request.send(this))
}