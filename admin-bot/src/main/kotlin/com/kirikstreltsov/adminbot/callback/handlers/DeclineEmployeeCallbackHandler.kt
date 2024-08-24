package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.client.EmployeeClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class DeclineEmployeeCallbackHandler(private val employeeClient: EmployeeClient) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("decline-employee:")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val chatId = callback.callbackQuery.message.chatId.toString()
        val employeeId = callback.callbackQuery.data.split(" ")[1].toLong()
        val responseCode = employeeClient.declineEmployee(employeeId)

        if (responseCode != 200) {
            val sendMessage = SendMessage(chatId, "Что-то пошло не так")
            absSender.execute(sendMessage)
            return
        }

        val deleteMessage = DeleteMessage(chatId, callback.callbackQuery.message.messageId)
        absSender.execute(deleteMessage)

        val sender = SendMessage(chatId, "Отказ успешно отправлен")
        absSender.execute(sender)
    }
}