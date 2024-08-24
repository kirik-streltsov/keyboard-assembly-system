package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.adminbot.session.KeyboardBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SwitchChosenCallbackHandler(private val sessionService: KeyboardBuildingSessionService) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("switch: ")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val switchId = callback.callbackQuery.data.split(" ")[1].toLong()
        val previousMessageId = callback.callbackQuery.message.messageId

        val chatId = callback.callbackQuery.message.chatId
        sessionService.setSwitch(chatId, switchId)

        val sendMessage = SendMessage(chatId.toString(), "Выбери кейкапы")
        sendMessage.replyMarkup = InlineKeyboardMarkupFactory.chooseKeycapInlineKeyboardMarkup()
        absSender.execute(sendMessage)

        val deleteMessage = DeleteMessage(chatId.toString(), previousMessageId)
        absSender.execute(deleteMessage)
    }
}