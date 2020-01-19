package com.davidsalas.proxy.exchange.service

import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class UriBuilderService {

    fun build(uri: String, params: Map<String, String>?): String {
        if (params.isNullOrEmpty()) return uri

        return UriComponentsBuilder
                .fromHttpUrl(uri)
                .buildAndExpand(params)
                .toUriString()
    }
}