package com.info.applicationauth.global.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfiguration {

    @Bean
    fun redisConnectionFactor(): RedisConnectionFactory {
        return LettuceConnectionFactory("redis", 6379)
    }

}
