package com.kirikstreltsov.adminbot.rabbitmq.listeners

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.dto.Employee
import com.kirikstreltsov.adminbot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.adminbot.formatters.EmployeeCreatedFormatter
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender

@EnableRabbit
@Component
class EmployeeCreatedListener(
    private val absSender: AbsSender,
    private val objectMapper: ObjectMapper,
    private val chatService: ChatService,
    private val formatter: EmployeeCreatedFormatter
) : RabbitMessageListener {

    @RabbitListener(queues = ["employee-created-queue"])
    override fun receiveMessage(message: String) {
        val adminIds = chatService.findAll() ?: emptyList()
        val employee = objectMapper.readValue(message, Employee::class.java)
        adminIds.forEach {
            val sendMessage = SendMessage(it.telegramId, formatter.format(employee))
            sendMessage.replyMarkup = InlineKeyboardMarkupFactory
                .acceptEmployeeInlineKeyboardMarkup(employee.telegramId)
            absSender.execute(sendMessage)
        }
    }
}