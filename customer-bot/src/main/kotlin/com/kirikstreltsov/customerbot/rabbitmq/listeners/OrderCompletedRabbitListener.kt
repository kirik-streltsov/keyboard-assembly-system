package com.kirikstreltsov.customerbot.rabbitmq.listeners

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.customerbot.dto.Order
import com.kirikstreltsov.customerbot.formatters.OrderCompletedFormatter
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
@EnableRabbit
class OrderCompletedRabbitListener(
    private val objectMapper: ObjectMapper,
    private val formatter: OrderCompletedFormatter,
    private val absSender: AbsSender
) : RabbitMessageListener {
    @RabbitListener(queues = ["order-completed-queue"])
    override fun receiveMessage(message: String) {
        val order = objectMapper.readValue(message, Order::class.java)

        val formattedText = formatter.format(order)
        val sendMessage = SendMessage(order.customer!!.telegramId.toString(), formattedText)

        absSender.execute(sendMessage)
    }
}