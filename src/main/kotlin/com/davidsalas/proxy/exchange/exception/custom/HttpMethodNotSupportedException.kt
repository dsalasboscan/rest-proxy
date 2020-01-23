package com.davidsalas.proxy.exchange.exception.custom

import com.davidsalas.proxy.exchange.exception.BadRequestException

class HttpMethodNotSupportedException(httpMethod: String) :
        BadRequestException("http method: $httpMethod not supported")