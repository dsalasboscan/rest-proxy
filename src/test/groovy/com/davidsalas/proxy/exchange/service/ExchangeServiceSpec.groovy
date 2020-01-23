package com.davidsalas.proxy.exchange.service

import com.davidsalas.proxy.exchange.exception.HttpException
import com.davidsalas.proxy.exchange.exception.custom.HttpMethodNotSupportedException
import com.davidsalas.proxy.exchange.model.Response
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.gateway.mvc.ProxyExchange
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import spock.lang.Specification

class ExchangeServiceSpec extends Specification {

    ProxyExchange proxyExchange
    ExchangeService exchangeService
    ObjectMapper mapper

    void setup() {
        proxyExchange = Mock(ProxyExchange)
        mapper = new ObjectMapper()
        exchangeService = new ExchangeService()
    }

    def "given a GET request to exchange, when call to service success, then return te Response"() {
        given:
        def httpMethod = "GET"
        def mockedResponse = mapper.readTree(defaultEntity())
        proxyExchange.get() >> new ResponseEntity(mockedResponse, HttpStatus.OK)

        when:
        def response = exchangeService.exchange(proxyExchange, httpMethod)

        then:
        response == new Response(mockedResponse, HttpStatus.OK)
    }

    def "given a POST request to exchange, when call to service success, then return the Response"() {
        given:
        def httpMethod = "POST"
        proxyExchange.post() >> new ResponseEntity(HttpStatus.CREATED)

        when:
        def response = exchangeService.exchange(proxyExchange, httpMethod)

        then:
        response == new Response(null, HttpStatus.CREATED)
    }

    def "given a GET request to exchange, when call to service failed with http 4xx, then return the error message and code"() {
        given:
        def httpMethod = "GET"
        proxyExchange.get() >> { throw new HttpClientErrorException(HttpStatus.CONFLICT) }

        when:
        exchangeService.exchange(proxyExchange, httpMethod)

        then:
        thrown(HttpException)
    }

    def "given a PUT request to exchange, when call to service failed with http 5xx, then return the error message and code"() {
        given:
        def httpMethod = "PUT"
        proxyExchange.put() >> { throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR) }

        when:
        exchangeService.exchange(proxyExchange, httpMethod)

        then:
        thrown(HttpException)
    }

    def "given a LINK request to exchange, when call to service failed with http 5xx, then return the error message and code"() {
        given:
        def httpMethod = "LINK"

        when:
        exchangeService.exchange(proxyExchange, httpMethod)

        then:
        thrown(HttpMethodNotSupportedException)
    }

    def defaultEntity() {
        """
            {
              "id": 33,
              "name": "Manchester United",
              "country": "England",
              "founded": 1878,
              "venue": {
                "name": "Old Trafford",
                "city": "Manchester",
                "capacity": 76212
              }
            }
        """
    }
}