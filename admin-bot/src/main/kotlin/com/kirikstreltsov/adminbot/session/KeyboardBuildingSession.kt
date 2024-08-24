package com.kirikstreltsov.adminbot.session

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("KeyboardBuildingSession")
data class KeyboardBuildingSession(
    @Id
    val chatId: Long,
    var caseId: Long? = null,
    var switchId: Long? = null,
    var keycapId: Long? = null
) : Serializable