package com.davidsalas.proxy.exchange.info

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProxiedServiceInfoController(
        private val proxiedServiceInfoService: ProxiedServiceInfoService
) {

    @GetMapping("/service-info/{serviceId}", produces = ["application/json"])
    fun getServiceInfo(@PathVariable("serviceId") serviceId: String): ResponseEntity<ProxiedServiceInfo> {
        return proxiedServiceInfoService.getServiceInfo(serviceId)
    }

    @PostMapping("/service-info", produces = ["application/json"])
    fun createServiceInfo(@RequestBody serviceInfo: ProxiedServiceInfo): ResponseEntity<ProxiedServiceInfo> {
        return proxiedServiceInfoService.createServiceInfo(serviceInfo)
    }

    @PutMapping("/service-info", produces = ["application/json"])
    fun modifyServiceInfo(@RequestBody serviceInfo: ProxiedServiceInfo): ResponseEntity<ProxiedServiceInfo> {
        return proxiedServiceInfoService.modifyServiceInfo(serviceInfo)
    }

    @DeleteMapping("/service-info/{serviceId}", produces = ["application/json"])
    fun deleteServiceInfo(@PathVariable("serviceId") serviceId: String): ResponseEntity<ProxiedServiceInfo> {
        return proxiedServiceInfoService.deleteServiceInfo(serviceId)
    }
}