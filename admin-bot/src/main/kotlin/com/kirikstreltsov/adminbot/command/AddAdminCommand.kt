package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class AddAdminCommand(private val chatService: ChatService) : AbstractCommand() {
    override val alias: String
        get() = "Добавить нового админа"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        val sendMessage = SendMessage(
            chat.telegramId, "Перешли сообщение человека с текстом \"привет\", которого хочешь добавить.\n" +
                    "Сообщение не должно иметь переходов на новую строку"
        )

        absSender.execute(sendMessage)

        chatService.setChatStateById(chat.telegramId, Chat.State.AWAITING_FORWARD)
    }
}