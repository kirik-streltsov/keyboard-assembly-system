package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.adminbot.session.ComponentBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class CreateComponentCommand(
    private val chatService: ChatService,
    private val sessionService: ComponentBuildingSessionService
) : AbstractCommand() {
    override val alias: String
        get() = "Добавить новый компонент"

    override fun execute(update: Update, absSender: AbsSender) {
        val sendMessage = SendMessage(update.message.chatId.toString(), "Выберите тип компонента")
        sendMessage.replyMarkup = InlineKeyboardMarkupFactory.createComponentTypesInlineKeyboardMarkup()
        absSender.execute(sendMessage)

        sessionService.createSession(update.message.chatId)
        chatService.setChatStateById(update.message.chatId.toString(), Chat.State.AWAITING_COMPONENT_NAME)
    }
}