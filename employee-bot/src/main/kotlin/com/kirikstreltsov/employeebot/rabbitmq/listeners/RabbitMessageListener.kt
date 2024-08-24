package com.kirikstreltsov.employeebot.rabbitmq.listeners

interface RabbitMessageListener {
    fun receiveMessage(message: String)
}