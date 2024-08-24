package com.kirikstreltsov.adminbot.rabbitmq.listeners

interface RabbitMessageListener {
    fun receiveMessage(message: String)
}