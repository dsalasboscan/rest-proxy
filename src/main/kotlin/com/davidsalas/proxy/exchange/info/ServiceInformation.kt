package com.davidsalas.proxy.exchange.info

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "serviceInfo")
class ServiceInformation {

    @Id
    lateinit var id: String

    lateinit var uri: String

    lateinit var httpMethod: String

    lateinit var inFunction: String

    lateinit var outFunction: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceInformation

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "ProxiedServiceInfo(id='$id', uri='$uri', httpMethod='$httpMethod', inFunction='$inFunction', " +
                "outFunction='$outFunction')"
    }
}
