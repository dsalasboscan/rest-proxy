package com.davidsalas.proxy.config

import com.davidsalas.proxy.exchange.info.ServiceInformation
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
            RedisTemplate<String, ServiceInformation> {

        val template = RedisTemplate<String, ServiceInformation>()
        template.setConnectionFactory(redisConnectionFactory)
        template.keySerializer = RedisSerializer.string()
        template.valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
        return template
    }
}