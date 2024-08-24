package com.kirikstreltsov.messaging

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class MessagingService(private val rabbitTemplate: RabbitTemplate) {
    fun sendMessage(message: String, routingKey: String) {
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_NAME, routingKey, message)
    }
}