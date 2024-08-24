package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.session.ComponentBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateComponentTypeCallbackHandler(
    private val componentBuildingSessionService: ComponentBuildingSessionService,
    private val chatService: ChatService
) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        println("checkPattern: $pattern")
        return pattern.startsWith("create-component:")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(callback.callbackQuery.message.chatId.toString()) ?: return

        val componentType = callback.callbackQuery.data.split(" ")[1]
        componentBuildingSessionService.setComponentType(chat.telegramId.toLong(), componentType)

        val sendMessage = SendMessage(chat.telegramId, "Введите имя компонента")
        absSender.execute(sendMessage)

        chatService.setChatStateById(chat.telegramId, Chat.State.AWAITING_COMPONENT_NAME)
    }
}