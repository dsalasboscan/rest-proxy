package com.davidsalas.proxy.exchange

import com.davidsalas.proxy.exchange.model.AssembleAbleRequest
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class ProxyController(val proxyService: ProxyService) {

    @RequestMapping(path = ["/proxy/service/{id}"], produces = ["application/json"])
    fun proxy(proxy: ProxyExchange<JsonNode>, request: HttpServletRequest,
              @PathVariable("id") serviceId: String,
              @RequestBody(required = false) body: JsonNode?,
              @RequestParam(required = false) params: Map<String, String>?,
              @RequestHeader headers: Map<String, String>): ResponseEntity<JsonNode> {

        return proxyService.execute(AssembleAbleRequest(proxy, serviceId, body, params, headers))
    }
}