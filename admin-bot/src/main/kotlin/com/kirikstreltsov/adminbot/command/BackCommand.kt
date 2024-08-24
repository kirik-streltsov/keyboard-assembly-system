package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.factory.ReplyKeyboardMarkupFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class BackCommand(private val chatService: ChatService) : AbstractCommand() {
    override val alias: String
        get() = "Назад"

    override fun execute(update: Update, absSender: AbsSender) {
        val sendMessage = SendMessage(update.message.chatId.toString(), "Окей, выбери действие")
        sendMessage.replyMarkup = ReplyKeyboardMarkupFactory.createAdminActionChoiceKeyboardMarkup()
        absSender.execute(sendMessage)

        chatService.setChatStateById(update.message.chatId.toString(), Chat.State.DEFAULT)
    }
}