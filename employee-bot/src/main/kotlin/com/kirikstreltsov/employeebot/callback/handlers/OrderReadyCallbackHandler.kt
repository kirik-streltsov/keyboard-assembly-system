package com.kirikstreltsov.employeebot.callback.handlers

import com.kirikstreltsov.employeebot.client.EmployeeClient
import com.kirikstreltsov.employeebot.client.OrderClient
import com.kirikstreltsov.employeebot.dto.ReadinessState
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class OrderReadyCallbackHandler(
    private val orderClient: OrderClient,
    private val employeeClient: EmployeeClient
) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("ready order:")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val chatId = callback.callbackQuery.message.chatId.toString()
        val orderId = callback.callbackQuery.data.split(" ")[2].toLong()

        val employee = employeeClient.getEmployeeByTelegramId(chatId.toLong())
        if (employee == null || !employee.approved) {
            val sendMessage = SendMessage(chatId, "Вы не являетесь сотрудником")
            absSender.execute(sendMessage)
            return
        }

        val code = orderClient.setOrderReadinessStateById(orderId, ReadinessState.COMPLETED)

        if (code != 200) {
            val sendMessage = SendMessage(chatId, "Что-то пошло не так")
            absSender.execute(sendMessage)
            return
        }

        val deleteMessage = DeleteMessage(chatId, callback.callbackQuery.message.messageId)
        absSender.execute(deleteMessage)

        val sender = SendMessage(chatId, "Заказ теперь помечен как готовый")
        absSender.execute(sender)
    }
}