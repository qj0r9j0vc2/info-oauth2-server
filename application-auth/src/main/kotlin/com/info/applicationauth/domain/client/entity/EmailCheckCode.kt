package com.info.applicationauth.domain.client.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash
class EmailCheckCode(
    email: String,
    data: String,
    ttl: Long = 60 * 5
) {

    @Id
    val email: String = email

    val data: String = data

    @TimeToLive
    var ttl: Long = ttl


}
