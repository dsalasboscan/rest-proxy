package com.davidsalas.proxy.config.cache

import com.davidsalas.proxy.exchange.info.ProxiedServiceInfo
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
class CacheConfiguration {

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory, objectMapper: ObjectMapper):
            RedisTemplate<String, ProxiedServiceInfo> {

        val template = RedisTemplate<String, ProxiedServiceInfo>()
        template.setConnectionFactory(redisConnectionFactory)
        template.keySerializer = RedisSerializer.string()
        template.valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
        return template
    }
}