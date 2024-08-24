package com.kirikstreltsov.customerbot.rabbitmq.listeners

import com.kirikstreltsov.customerbot.configuration.RabbitConfiguration
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
@EnableRabbit
class EmployeeDeclinedRabbitListener(
    private val absSender: AbsSender
) : RabbitMessageListener {
    @RabbitListener(queues = [RabbitConfiguration.EMPLOYEE_DECLINED_QUEUE])
    override fun receiveMessage(message: String) {
        val sendMessage = SendMessage(message, "К сожалению, тебя не приняли в команду :(")
        absSender.execute(sendMessage)
    }
}