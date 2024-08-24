package com.kirikstreltsov.customerbot.rabbitmq.listeners

interface RabbitMessageListener {
    fun receiveMessage(message: String)
}