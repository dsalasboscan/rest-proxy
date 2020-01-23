package com.davidsalas.proxy.exchange.info

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ServiceInformationController(
        private val serviceInformationService: ServiceInformationService
) {

    @GetMapping("/service-info/{serviceId}", produces = ["application/json"])
    fun getServiceInfo(@PathVariable("serviceId") serviceId: String): ResponseEntity<ServiceInformation> {
        return serviceInformationService.getServiceInfo(serviceId)
    }

    @PostMapping("/service-info", produces = ["application/json"])
    fun createServiceInfo(@RequestBody serviceInformation: ServiceInformation): ResponseEntity<ServiceInformation> {
        return serviceInformationService.createServiceInfo(serviceInformation)
    }

    @PutMapping("/service-info", produces = ["application/json"])
    fun modifyServiceInfo(@RequestBody serviceInformation: ServiceInformation): ResponseEntity<ServiceInformation> {
        return serviceInformationService.modifyServiceInfo(serviceInformation)
    }

    @DeleteMapping("/service-info/{serviceId}", produces = ["application/json"])
    fun deleteServiceInfo(@PathVariable("serviceId") serviceId: String): ResponseEntity<ServiceInformation> {
        return serviceInformationService.deleteServiceInfo(serviceId)
    }
}