package com.davidsalas.proxy.exchange.exception.custom

import com.davidsalas.proxy.exchange.exception.NotFoundException

class ServiceInfoNotFoundException(id: String) :
        NotFoundException("There is not service with id: $id to be proxied")