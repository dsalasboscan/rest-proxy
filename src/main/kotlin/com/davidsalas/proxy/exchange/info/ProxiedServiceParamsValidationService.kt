package com.davidsalas.proxy.exchange.info

import com.davidsalas.proxy.exchange.mutate.FunctionExecutorService
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.stereotype.Service

@Service
class ProxiedServiceParamsValidationService {

    fun validateParams(uri: String, inFunction: String, outFunction: String) {
        validateFunctionName(inFunction, outFunction)
        validateUriFormat(uri)
    }

    private fun validateFunctionName(inFunction: String, outFunction: String) {
        FunctionExecutorService.InFunction.from(inFunction)
        FunctionExecutorService.OutFunction.from(outFunction)
    }

    private fun validateUriFormat(uri: String) {
        val validator = UrlValidator()
        validator.isValid(uri)
    }
}