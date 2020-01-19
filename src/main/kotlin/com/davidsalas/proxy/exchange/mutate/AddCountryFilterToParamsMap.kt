package com.davidsalas.proxy.exchange.mutate

fun addCountryFilterToParamsMap(params: MutableMap<String, String>, headers: Map<String, String>) {
    val countryName = headers[ParamKeys.COUNTRY_NAME.name.toLowerCase()] ?: ""
    params[ParamKeys.COUNTRY_NAME.name] = countryName
}