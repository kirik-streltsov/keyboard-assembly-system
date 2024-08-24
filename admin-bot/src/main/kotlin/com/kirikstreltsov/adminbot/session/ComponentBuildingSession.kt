package com.kirikstreltsov.adminbot.session

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("ComponentBuildingSession")
data class ComponentBuildingSession(
    @Id
    val chatId: Long,
    var componentType: String? = null,
    var componentName: String? = null,
    var componentPrice: Double? = null,
)