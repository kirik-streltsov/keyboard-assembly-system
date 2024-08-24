package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.session.KeyboardBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class OrderDeclinedCallbackHandler(private val sessionService: KeyboardBuildingSessionService) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern == "do not create order"
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val chatId = callback.callbackQuery.message.chatId
        val previousMessageId = callback.callbackQuery.message.messageId

        sessionService.clearSession(chatId)

        val deleteMessage = DeleteMessage(chatId.toString(), previousMessageId)
        absSender.execute(deleteMessage)
    }
}