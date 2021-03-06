package com.davidsalas.proxy.exchange.info

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ServiceInformationRepository : MongoRepository<ServiceInformation, String>