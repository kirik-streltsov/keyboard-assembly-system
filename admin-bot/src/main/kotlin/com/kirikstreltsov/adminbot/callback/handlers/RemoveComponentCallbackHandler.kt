package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.client.ComponentsClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class RemoveComponentCallbackHandler(
    private val chatService: ChatService,
    private val client: ComponentsClient)
    : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return Regex("remove-(case|switch|keycap)-id: [0-9]+").matches(pattern)
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {

        val chat = chatService.findChatByTelegramId(callback.callbackQuery.message.chatId.toString()) ?: return
        val componentType = callback.callbackQuery.data.split("-")[1]
        val componentId = callback.callbackQuery.data.split(" ")[1].toLong()

        val deleteMessage = DeleteMessage(chat.telegramId, callback.callbackQuery.message.messageId)
        absSender.execute(deleteMessage)

        val method = when (componentType) {
            "case" -> client::removeCaseById
            "switch" -> client::removeSwitchById
            else -> client::removeKeycapById
        }
        method.invoke(componentId)

        val sendMessage = SendMessage(chat.telegramId, "Компонент удален")
        absSender.execute(sendMessage)

        chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
    }
}