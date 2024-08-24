package com.kirikstreltsov.employeebot.command

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
class FireCommand(private val client: EmployeeClient, private val chatService: ChatService) : AbstractCommand() {
    override val alias: String
        get() = "Уволиться"

    override fun execute(update: Update, absSender: AbsSender) {
        val employeeId = update.message.chatId
        val code = client.fireEmployeeByTelegramId(employeeId)

        if (code != 200) {
            val sendMessage = SendMessage(employeeId.toString(), "Что-то пошло не так")
            absSender.execute(sendMessage)
            return
        }

        val sendMessage = SendMessage(employeeId.toString(), "Вы успешно уволились")
        sendMessage.replyMarkup = ReplyKeyboardMarkupFactory.createLoginKeyboardMarkup()
        absSender.execute(sendMessage)

        chatService.setChatStateById(employeeId.toString(), Chat.State.DEFAULT)
    }
}