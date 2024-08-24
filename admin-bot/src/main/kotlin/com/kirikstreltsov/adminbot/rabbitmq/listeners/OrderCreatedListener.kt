package com.kirikstreltsov.adminbot.rabbitmq.listeners

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.dto.Order
import com.kirikstreltsov.adminbot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.adminbot.formatters.OrderCreatedChannelMessageFormatter
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender

@EnableRabbit
@Component
class OrderCreatedListener(
    private val absSender: AbsSender,
    private val objectMapper: ObjectMapper,
    private val formatter: OrderCreatedChannelMessageFormatter,
    @Value("\${channel.id}")
    private val channelId: Long
) : RabbitMessageListener {
    @RabbitListener(queues = ["order-created-queue"])
    override fun receiveMessage(message: String) {
        val order = objectMapper.readValue(message, Order::class.java)
        val sendMessage = SendMessage(channelId.toString(), formatter.format(order))
        absSender.execute(sendMessage)
    }
}