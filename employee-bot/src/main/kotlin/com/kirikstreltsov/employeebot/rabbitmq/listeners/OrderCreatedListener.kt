package com.kirikstreltsov.employeebot.rabbitmq.listeners

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.employeebot.chat.ChatService
import com.kirikstreltsov.employeebot.dto.Order
import com.kirikstreltsov.employeebot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.employeebot.formatters.EmployeeOrderFormatter
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender

@EnableRabbit
@Component
class OrderCreatedListener(
    private val absSender: AbsSender,
    private val objectMapper: ObjectMapper,
    private val formatter: EmployeeOrderFormatter,
    private val chatService: ChatService
) : RabbitMessageListener {
    @RabbitListener(queues = ["order-created-queue"])
    override fun receiveMessage(message: String) {
        val employeeIds = chatService.findAll() ?: emptyList()
        val order = objectMapper.readValue(message, Order::class.java)

        employeeIds.forEach {
            val employeeSendMessage = SendMessage(it.telegramId, formatter.format(order))
            employeeSendMessage.replyMarkup = InlineKeyboardMarkupFactory.orderAcceptInlineKeyboardMarkup(order.id)
            absSender.execute(employeeSendMessage)
        }
    }
}