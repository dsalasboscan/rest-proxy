package com.davidsalas.proxy.exchange.mutate

enum class InAvailableFunction(val value: String) {
    DEFAULT("DEFAULT"),
    IN_FILTER_TEAMS("FILTER_TEAMS");

    companion object {
        private val map = values().associateBy(InAvailableFunction::value)
        @JvmStatic
        fun from(value: String) = map[value]
                ?: throw Exception("No existe una funcion de entrada con ese nombre: $value")
    }
}

enum class OutAvailableFunction(val value: String) {
    DEFAULT("DEFAULT"),
    OUT_FILTER_TEAMS("FILTER_TEAMS");

    companion object {
        private val map = values().associateBy(OutAvailableFunction::value)
        @JvmStatic
        fun from(value: String) = map[value]
                ?: throw Exception("No existe una funcion de salida con ese nombre: $value")
    }
}