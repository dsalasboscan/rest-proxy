package com.davidsalas.proxy.exchange.info

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "serviceInfo")
class ProxiedServiceInfo {

    @Id
    lateinit var id: String

    lateinit var uri: String

    lateinit var httpMethod: String

    lateinit var inFunction: String

    lateinit var outFunction: String

}
