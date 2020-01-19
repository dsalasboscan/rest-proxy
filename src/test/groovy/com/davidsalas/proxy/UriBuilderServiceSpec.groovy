package com.davidsalas.proxy

import com.davidsalas.proxy.exchange.service.UriBuilderService
import spock.lang.Specification

class UriBuilderServiceSpec extends Specification {

    def "Given a request with 1 path parameter, then return a correct URL"() {
        given:
        def urlToFormat = "http://localhost:8080/service/{param}"
        def params = new HashMap<String, String>()
        params.put("param", "1")

        when:
        def response = new UriBuilderService().build(urlToFormat, params)

        then:
        response == "http://localhost:8080/service/1"
    }

    def "Given a request with 2 path parameters, then return a correct URL"() {
        given:
        def urlToFormat = "http://localhost:8080/service/{param1}/path/{param2}"
        def params = new HashMap<String, String>()
        params.put("param1", "1")
        params.put("param2", "2")

        when:
        def response = new UriBuilderService().build(urlToFormat, params)

        then:
        response == "http://localhost:8080/service/1/path/2"
    }

    def "Given a request with 1 queryString parameter, then return a correct URL"() {
        given:
        def urlToFormat = "http://localhost:8080/service?param={param1}"
        def params = new HashMap<String, String>()
        params.put("param1", "1")

        when:
        def response = new UriBuilderService().build(urlToFormat, params)

        then:
        response == "http://localhost:8080/service?param=1"
    }

    def "Given a request with 2 queryString parameter, then return a correct URL"() {
        given:
        def urlToFormat = "http://localhost:8080/service?param={param1}&param2={param2}"
        def params = new HashMap<String, String>()
        params.put("param1", "1")
        params.put("param2", "2")

        when:
        def response = new UriBuilderService().build(urlToFormat, params)

        then:
        response == "http://localhost:8080/service?param=1&param2=2"
    }

    def "Given a url that doesn't have parameters, then returns the original url"() {
        given:
        def urlToNotFormat = "http://localhost:8080/service/gcba"

        when:
        def response = new UriBuilderService().build(urlToNotFormat, null)

        then:
        response == urlToNotFormat
    }

    def "Given a url that doesn't have parameters, when try to pass a param, then returns the original url"() {
        given:
        def urlToNotFormat = "http://localhost:8080/service/gcba"
        def params = new HashMap<String, String>()
        params.put("param1", "1")

        when:
        def response = new UriBuilderService().build(urlToNotFormat, params)

        then:
        response == urlToNotFormat
    }
}