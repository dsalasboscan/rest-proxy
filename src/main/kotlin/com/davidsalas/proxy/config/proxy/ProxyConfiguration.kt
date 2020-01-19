package com.davidsalas.proxy.config.proxy

import org.springframework.cloud.gateway.mvc.config.ProxyExchangeArgumentResolver
import org.springframework.cloud.gateway.mvc.config.ProxyProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ProxyConfiguration {

    @Bean
    fun proxyArgumentResolver(restTemplate: RestTemplate, proxyProperties: ProxyProperties): ProxyExchangeArgumentResolver {
        val resolver = ProxyExchangeArgumentResolver(restTemplate)
        resolver.setHeaders(proxyProperties.convertHeaders())
        return resolver
    }
}