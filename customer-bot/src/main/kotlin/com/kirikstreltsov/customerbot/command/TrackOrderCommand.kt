package com.kirikstreltsov.customerbot.command

import com.kirikstreltsov.customerbot.annotation.CommandBean
import com.kirikstreltsov.customerbot.chat.Chat
import com.kirikstreltsov.customerbot.chat.ChatService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class TrackOrderCommand(
    private val chatService: ChatService
) : AbstractCommand() {
    override val alias: String
        get() = "Отследить заказ"

    override fun execute(update: Update, absSender: AbsSender) {
        val chatTelegramId = update.message.chatId.toString()

        chatService.setChatStateById(chatTelegramId, Chat.State.AWAITING_ORDER_ID)

        val sendMessage = SendMessage(chatTelegramId, "Введи номер заказа")
        absSender.execute(sendMessage)
    }
}