package com.davidsalas.proxy.exchange.info

import com.davidsalas.proxy.cache.CacheService
import com.davidsalas.proxy.exchange.exception.custom.ServiceInfoNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.util.stream.Collectors

class ServiceInfoServiceSpec extends Specification {

    ServiceInformationService serviceInfoService

    ServiceInformationRepository serviceInfoRepository
    CacheService cacheService

    void setup() {
        serviceInfoRepository = Mock()
        cacheService = Mock()

        serviceInfoService = new ServiceInformationService(serviceInfoRepository, cacheService)
    }

    def "given serviceId that is not cached but exist on DB return the serviceInfo"() {
        given:
        def serviceId = "id"
        cacheService.get(serviceId) >> null
        serviceInfoRepository.findById(serviceId) >> Optional.of(defaultServiceInfo())

        when:
        def response = serviceInfoService.getServiceInfoData(serviceId)

        then:
        response == defaultServiceInfo()
        1 * cacheService.get(serviceId)
    }

    def "given serviceId that is not cached and dont exist on DB throw exception"() {
        given:
        def serviceId = "id"
        cacheService.get(serviceId) >> null
        serviceInfoRepository.findById(serviceId) >> Optional.empty()

        when:
        serviceInfoService.getServiceInfoData(serviceId)

        then:
        thrown(ServiceInfoNotFoundException)
    }

    def "given serviceId that is cached return it and dont call to the database"() {
        given:
        def serviceId = "id"
        cacheService.get(serviceId) >> defaultServiceInfo()

        when:
        def response = serviceInfoService.getServiceInfoData(serviceId)

        then:
        response == defaultServiceInfo()
        0 * serviceInfoRepository.findById(serviceId)
    }

    def "given serviceId return the responseEntity with code OK"() {
        given:
        def serviceId = "id"
        cacheService.get(serviceId) >> null
        serviceInfoRepository.findById(serviceId) >> Optional.of(defaultServiceInfo())

        when:
        def response = serviceInfoService.getServiceInfo(serviceId)

        then:
        response == new ResponseEntity(defaultServiceInfo(), HttpStatus.OK)
        1 * cacheService.get(serviceId)
    }

    def "given a proxiedService to save on the DB return the responseEntity with code CREATED"() {
        given:
        def serviceId = "id"
        def proxiedService = defaultServiceInfo()
        cacheService.save(serviceId, defaultServiceInfo()) >> null
        serviceInfoRepository.insert(proxiedService) >> null

        when:
        def response = serviceInfoService.createServiceInfo(proxiedService)

        then:
        response == new ResponseEntity(HttpStatus.CREATED)
        1 * serviceInfoRepository.insert(proxiedService)
    }

    def "given a proxiedService to modify on the DB return the responseEntity with code NO_CONTENT"() {
        given:
        def serviceId = "id"
        def proxiedService = defaultServiceInfo()
        cacheService.save(serviceId, defaultServiceInfo()) >> null
        serviceInfoRepository.save(proxiedService) >> null

        when:
        def response = serviceInfoService.modifyServiceInfo(proxiedService)

        then:
        response == new ResponseEntity(HttpStatus.NO_CONTENT)
        1 * serviceInfoRepository.save(proxiedService)
    }

    def "given a serviceId to delete on the DB return the responseEntity with code NO_CONTENT"() {
        given:
        def serviceId = "id"
        cacheService.delete(serviceId) >> null
        serviceInfoRepository.deleteById(serviceId) >> null

        when:
        def response = serviceInfoService.deleteServiceInfo(serviceId)

        then:
        response == new ResponseEntity(HttpStatus.NO_CONTENT)
        1 * serviceInfoRepository.deleteById(serviceId)
    }

    private static def defaultServiceInfo() {
        def mockedServiceInfo = new ServiceInformation()
        mockedServiceInfo.setId("id")
        mockedServiceInfo.setUri("http://localhost:8080")
        mockedServiceInfo.setHttpMethod("POST")
        mockedServiceInfo.setInFunction("DEFAULT")
        mockedServiceInfo.setOutFunction("DEFAULT")
        return mockedServiceInfo
    }

    private static singleLine(String content) {
        content.trim().lines().map({ line -> line.trim() }).collect(Collectors.joining())
    }

}
