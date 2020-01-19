package com.davidsalas.proxy.exchange.service

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.gateway.mvc.ProxyExchange

fun removeSensitiveHeaders(proxyExchange: ProxyExchange<JsonNode>, headers: Map<String, String>) {
    val proxyHeaderBase = "proxy_param_header_"
    headers.forEach { (k) -> if (k.contains(proxyHeaderBase)) proxyExchange.sensitive(k) }
}