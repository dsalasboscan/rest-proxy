package com.davidsalas.proxy.cache

import com.davidsalas.proxy.exchange.info.ServiceInformation
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CacheService(
        private val redisTemplate: RedisTemplate<String, String>,
        private val mapper: ObjectMapper
) {

    @Value("\${proxy.service-info.cache.ttl}")
    private var ttl: Long = 0

    fun save(key: String, value: ServiceInformation) {
        redisTemplate.opsForValue().set(key, mapper.writeValueAsString(value), ttl, TimeUnit.SECONDS)
    }

    fun get(key: String): ServiceInformation? {
        redisTemplate.opsForValue().get(key) ?: return null
        return mapper.readValue(key, ServiceInformation::class.java)
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }
}