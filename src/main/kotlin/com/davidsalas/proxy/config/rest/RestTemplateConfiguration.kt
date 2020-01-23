package com.davidsalas.proxy.config.rest

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration


@Configuration
class RestTemplateConfiguration {

    val readTimeout = 15000L

    val connectionTimeout = 10000L

    @Bean
    fun defaultRestTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate = restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(connectionTimeout))
            .setReadTimeout(Duration.ofSeconds(readTimeout))
            .build()
}