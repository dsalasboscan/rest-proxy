package com.davidsalas.proxy.exchange.info

import com.davidsalas.proxy.cache.CacheService
import com.davidsalas.proxy.exchange.exception.custom.ServiceInfoNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ServiceInformationService(
        private val serviceInformationRepository: ServiceInformationRepository,
        private val cacheService: CacheService
) {

    fun getServiceInfoData(serviceId: String): ServiceInformation {
        return tryToRetrieveFromCache(serviceId)
                ?: tryToRetrieveFromDatabase(serviceId)
    }

    fun getServiceInfo(serviceId: String): ResponseEntity<ServiceInformation> =
            ResponseEntity(this.getServiceInfoData(serviceId), HttpStatus.OK)

    fun createServiceInfo(serviceInformation: ServiceInformation): ResponseEntity<ServiceInformation> =
            save(serviceInformation, HttpStatus.CREATED) { serviceInformationRepository.insert(serviceInformation) }

    fun modifyServiceInfo(serviceInformation: ServiceInformation): ResponseEntity<ServiceInformation> =
            save(serviceInformation, HttpStatus.NO_CONTENT) { serviceInformationRepository.save(serviceInformation) }

    fun deleteServiceInfo(serviceId: String): ResponseEntity<ServiceInformation> {
        val response = toResponse(HttpStatus.NO_CONTENT) {
            serviceInformationRepository.deleteById(serviceId)
        }
        deleteFromCache(serviceId)
        return response
    }

    private fun tryToRetrieveFromCache(serviceId: String): ServiceInformation? = cacheService.get(serviceId)

    private fun tryToRetrieveFromDatabase(serviceId: String): ServiceInformation =
            serviceInformationRepository.findById(serviceId).orElseThrow { throw ServiceInfoNotFoundException(serviceId) }

    private fun save(serviceInformation: ServiceInformation, httpStatus: HttpStatus, block: (ServiceInformation) -> Unit):
            ResponseEntity<ServiceInformation> {

        validateParams(serviceInformation.uri, serviceInformation.inFunction, serviceInformation.outFunction)
        val response = toResponse(httpStatus) { block(serviceInformation) }
        saveInCache(serviceInformation)
        return response
    }

    private fun saveInCache(serviceInformation: ServiceInformation) =
            cacheService.save(serviceInformation.id, serviceInformation)

    private fun deleteFromCache(serviceId: String) = cacheService.delete(serviceId)

    private fun toResponse(httpStatus: HttpStatus, block: () -> Unit): ResponseEntity<ServiceInformation> {
        try {
            block()
            return ResponseEntity(httpStatus)
        } catch (e: IllegalArgumentException) {
            throw e
        }
    }
}