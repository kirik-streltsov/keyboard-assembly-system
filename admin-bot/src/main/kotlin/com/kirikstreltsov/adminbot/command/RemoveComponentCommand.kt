package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.factory.InlineKeyboardMarkupFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class RemoveComponentCommand(private val chatService: ChatService) : AbstractCommand() {
    override val alias: String
        get() = "Удалить компонент"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        val sendMessage = SendMessage(chat.telegramId, "Выбери тип компонента, который хочешь удалить")
        sendMessage.replyMarkup = InlineKeyboardMarkupFactory.removeComponentTypesInlineKeyboardMarkup()
        absSender.execute(sendMessage)

        chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
    }
}