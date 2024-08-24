package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.client.EmployeeClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class AcceptEmployeeCallbackHandler(private val client: EmployeeClient) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("accept-employee:")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val employeeId = callback.callbackQuery.data.split(" ")[1].toLong()
        val currentChatId = callback.callbackQuery.message.chatId
        val responseCode = client.approveEmployee(employeeId)

        if (responseCode != 200) {
            val sendMessage = SendMessage(currentChatId.toString(), "Что-то пошло не так")
            absSender.execute(sendMessage)
            return
        }

        val deleteMessage = DeleteMessage(currentChatId.toString(), callback.callbackQuery.message.messageId)
        absSender.execute(deleteMessage)

        val sendMessage = SendMessage(currentChatId.toString(), "Сотрудник принят!")
        absSender.execute(sendMessage)
    }
}