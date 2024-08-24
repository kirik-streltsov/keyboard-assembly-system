package com.kirikstreltsov.customerbot.rabbitmq.listeners

import com.kirikstreltsov.customerbot.configuration.RabbitConfiguration
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
@EnableRabbit
class EmployeeApprovedRabbitListener(
    private val absSender: AbsSender
) : RabbitMessageListener {
    @RabbitListener(queues = [RabbitConfiguration.EMPLOYEE_APPROVE_QUEUE])
    override fun receiveMessage(message: String) {
        val sendMessage = SendMessage(message, "Тебя приняли в команду, урааа\nНапиши боту: @KeyboardEmployeeBot")
        absSender.execute(sendMessage)
    }
}