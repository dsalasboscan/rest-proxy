package com.davidsalas.proxy.exchange.info

import com.davidsalas.proxy.exchange.mutate.InAvailableFunction
import com.davidsalas.proxy.exchange.mutate.OutAvailableFunction
import org.apache.commons.validator.routines.UrlValidator

fun validateParams(uri: String, inFunction: String, outFunction: String) {
    fun validateUriFormat() {
        val validator = UrlValidator()
        validator.isValid(uri)
    }

    fun validateFunctionName() {
        InAvailableFunction.from(inFunction)
        OutAvailableFunction.from(outFunction)
    }

    validateFunctionName()
    validateUriFormat()
}