package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.client.OrderClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class AcceptOrderCallbackHandler(private val orderClient: OrderClient) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("accept order:")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val orderId = callback.callbackQuery.data.split(" ")[2].toLong()
        val order = orderClient.getOrderById(orderId)

        val chatId = callback.callbackQuery.message.chatId
        val sendMessage = SendMessage().apply { this.chatId = chatId.toString() }

        if (order.employee != null) {
            sendMessage.text = if (order.employee.telegramId == chatId) {
                "Вы уже работаете над этим заказом"
            } else {
                "Над этим заказом работает другой сотрудник"
            }

            absSender.execute(sendMessage)
            return
        }

        orderClient.setEmployeeToOrder(orderId, chatId)

        sendMessage.text = "Теперь вы работаете над заказом $orderId"
        absSender.execute(sendMessage)
    }
}