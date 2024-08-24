package com.kirikstreltsov.adminbot.chat

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("Chats")
data class Chat(@Id val telegramId: String, var state: String = State.DEFAULT)
    : Serializable {
    sealed class State {
        companion object {
            const val DEFAULT = "DEFAULT"
            const val AWAITING_ORDER_ID = "AWAITING_ORDER_ID"
            const val AWAITING_FORWARD = "AWAITING_FORWARD"
            const val AWAITING_COMPONENT_NAME = "AWAITING_COMPONENT_NAME"
            const val AWAITING_COMPONENT_PRICE = "AWAITING_COMPONENT_PRICE"
        }
    }
}