package com.davidsalas.proxy.exchange.info

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProxiedServiceInfoService(
        private val serviceInfoRepository: ProxiedServiceInfoRepository,
        private val paramsValidationService: ProxiedServiceParamsValidationService,
        private val redisTemplate: RedisTemplate<String, String>,
        private val mapper: ObjectMapper
) {

    fun getServiceInfoData(serviceId: String): ProxiedServiceInfo {
        return tryToRetrieveFromCache(serviceId)
                ?: tryToRetrieveFromDatabase(serviceId)
    }

    fun getServiceInfo(serviceId: String): ResponseEntity<ProxiedServiceInfo> {
        return ResponseEntity(this.getServiceInfoData(serviceId), HttpStatus.OK)
    }

    fun createServiceInfo(proxiedServiceInfo: ProxiedServiceInfo): ResponseEntity<ProxiedServiceInfo> {
        paramsValidationService.validateParams(proxiedServiceInfo.uri, proxiedServiceInfo.inFunction, proxiedServiceInfo.outFunction)
        val response = toResponse(HttpStatus.CREATED) {
            serviceInfoRepository.insert(proxiedServiceInfo)
        }
        saveInCache(proxiedServiceInfo)
        return response
    }

    fun modifyServiceInfo(proxiedServiceInfo: ProxiedServiceInfo): ResponseEntity<ProxiedServiceInfo> {
        paramsValidationService.validateParams(proxiedServiceInfo.uri, proxiedServiceInfo.inFunction, proxiedServiceInfo.outFunction)
        val response = toResponse(HttpStatus.NO_CONTENT) {
            serviceInfoRepository.save(proxiedServiceInfo)
        }
        saveInCache(proxiedServiceInfo)
        return response
    }

    fun deleteServiceInfo(serviceId: String): ResponseEntity<ProxiedServiceInfo> {
        val response = toResponse(HttpStatus.NO_CONTENT) {
            serviceInfoRepository.deleteById(serviceId)
        }
        deleteFromCache(serviceId)
        return response
    }

    private fun tryToRetrieveFromCache(serviceId: String): ProxiedServiceInfo? {
        return mapper.readValue(redisTemplate.opsForValue().get(serviceId), ProxiedServiceInfo::class.java)
    }

    private fun tryToRetrieveFromDatabase(serviceId: String): ProxiedServiceInfo {
        return serviceInfoRepository.findById(serviceId).orElseThrow { throw Exception() }
    }

    private fun saveInCache(proxiedServiceInfo: ProxiedServiceInfo) {
        redisTemplate.opsForValue().set(proxiedServiceInfo.id, mapper.writeValueAsString(proxiedServiceInfo))
    }

    private fun deleteFromCache(serviceId: String) {
        redisTemplate.delete(serviceId)
    }

    private fun toResponse(httpStatus: HttpStatus, block: () -> Unit): ResponseEntity<ProxiedServiceInfo> {
        try {
            block()
            return ResponseEntity(httpStatus)
        } catch (e: IllegalArgumentException) {
            throw e
        }
    }
}