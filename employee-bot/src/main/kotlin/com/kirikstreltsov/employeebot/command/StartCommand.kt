package com.kirikstreltsov.employeebot.command

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.employeebot.annotation.CommandBean
import com.kirikstreltsov.employeebot.chat.Chat
import com.kirikstreltsov.employeebot.chat.ChatService
import com.kirikstreltsov.employeebot.client.EmployeeClient
import com.kirikstreltsov.employeebot.factory.ReplyKeyboardMarkupFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class StartCommand(
    private val employeeClient: EmployeeClient,
    private val chatService: ChatService,
) : AbstractCommand() {
    override val alias: String
        get() = "/start"

    override fun execute(update: Update, absSender: AbsSender) {
        val employee = employeeClient.getEmployeeByTelegramId(update.message.chatId)

        if (employee == null || !employee.approved) {
            absSender.execute(SendMessage(update.message.chatId.toString(), "Вы не являетесь сотрудником"))
            return
        }

        val sendMessage = SendMessage
            .builder()
            .chatId(update.message.chatId.toString())
            .text("Привет! Выбери, что хочешь сделать")
            .replyMarkup(ReplyKeyboardMarkupFactory.createEmployeeActionChoiceKeyboardMarkup())
            .build()

        chatService.save(Chat(update.message.chatId.toString()))
        absSender.execute(sendMessage)
    }
}