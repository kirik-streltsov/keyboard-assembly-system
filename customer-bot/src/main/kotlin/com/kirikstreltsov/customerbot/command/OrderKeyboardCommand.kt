package com.kirikstreltsov.customerbot.command

import com.kirikstreltsov.customerbot.chat.Chat
import com.kirikstreltsov.customerbot.chat.ChatService
import com.kirikstreltsov.customerbot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.customerbot.session.KeyboardBuildingSessionService
import com.kirikstreltsov.customerbot.annotation.CommandBean
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class OrderKeyboardCommand(
    private val chatService: ChatService,
    private val sessionService: KeyboardBuildingSessionService
) : AbstractCommand() {
    override val alias: String
        get() = "Заказать клавиатуру"

    override fun execute(update: Update, absSender: AbsSender) {
        sessionService.createSession(update.message.chatId)

        val sendMessage = SendMessage(update.message.chatId.toString(), "Выбери кейс")
        sendMessage.replyMarkup = InlineKeyboardMarkupFactory.chooseCaseInlineKeyboardMarkup()

        absSender.execute(sendMessage)
        chatService.setChatStateById(update.message.chatId.toString(), Chat.State.DEFAULT)
    }
}