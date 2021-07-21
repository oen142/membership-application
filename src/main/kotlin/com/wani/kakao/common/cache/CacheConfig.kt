package com.wani.kakao.common.cache

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration


@Configuration
class CacheConfig {
    @Value("\${spring.redis.cache.host}")
    private val hostName: String? = null

    @Value("\${spring.redis.cache.port}")
    private val port = 0

    @Bean(name = ["redisCacheConnectionFactory"])
    fun redisCacheConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration =
            RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = hostName!!
        redisStandaloneConfiguration.port = port
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun cacheManager(@Qualifier("redisCacheConnectionFactory") connectionFactory: RedisConnectionFactory?): RedisCacheManager {
        val defaultConfig =
            RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        GenericJackson2JsonRedisSerializer()
                    )
                )
        val redisCacheConfigMap: MutableMap<String, RedisCacheConfiguration> =
            HashMap()
        redisCacheConfigMap[CacheName.MEMBERSHIP] = defaultConfig.entryTtl(Duration.ofHours(1))
        return RedisCacheManager.builder(connectionFactory!!)
            .withInitialCacheConfigurations(redisCacheConfigMap)
            .build()
    }
}
