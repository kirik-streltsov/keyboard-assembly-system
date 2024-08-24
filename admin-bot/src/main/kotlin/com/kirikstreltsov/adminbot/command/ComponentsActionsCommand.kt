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
class ComponentsActionsCommand(private val chatService: ChatService) : AbstractCommand() {
    override val alias: String
        get() = "Компоненты"

    override fun execute(update: Update, absSender: AbsSender) {
        val chatId = update.message.chatId.toString()
        val sendMessage = SendMessage(chatId, "Выбери действие")
        sendMessage.replyMarkup = ReplyKeyboardMarkupFactory.createComponentsActionsKeyboardMarkup()
        absSender.execute(sendMessage)

        chatService.setChatStateById(chatId, Chat.State.DEFAULT)
    }
}