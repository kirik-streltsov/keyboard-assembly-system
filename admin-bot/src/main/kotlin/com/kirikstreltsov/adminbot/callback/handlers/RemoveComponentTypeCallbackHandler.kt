package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.client.ComponentsClient
import com.kirikstreltsov.adminbot.factory.InlineKeyboardMarkupFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class RemoveComponentTypeCallbackHandler(
    private val chatService: ChatService,
    private val client: ComponentsClient)
    : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("remove-component-type:")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(callback.callbackQuery.message.chatId.toString()) ?: return
        val componentType = callback.callbackQuery.data.split(" ")[1]

        val method = when(componentType) {
            "case" -> client::getAllCases
            "switch" -> client::getAllSwitches
            else -> client::getAllKeycaps
        }

        val sendMessage = SendMessage(
            callback.callbackQuery.message.chatId.toString(),
            "Выбери компонент который хочешь удалить"
        )

        val components = method.invoke()
        sendMessage.replyMarkup = InlineKeyboardMarkupFactory.removeComponentChoiceInlineKeyboardMarkup(
            components,
            componentType
        )

        absSender.execute(sendMessage)
        chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
    }
}