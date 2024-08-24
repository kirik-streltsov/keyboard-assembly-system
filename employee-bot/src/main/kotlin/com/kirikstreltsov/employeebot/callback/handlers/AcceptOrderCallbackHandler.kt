package com.kirikstreltsov.employeebot.callback.handlers

import com.kirikstreltsov.employeebot.client.EmployeeClient
import com.kirikstreltsov.employeebot.client.OrderClient
import com.kirikstreltsov.employeebot.dto.Employee
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class AcceptOrderCallbackHandler(
    private val orderClient: OrderClient,
    private val employeeClient: EmployeeClient
) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("accept order:")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val orderId = callback.callbackQuery.data.split(" ")[2].toLong()
        val order = orderClient.getOrderById(orderId)

        val chatId = callback.callbackQuery.message.chatId

        val employee = employeeClient.getEmployeeByTelegramId(chatId)
        if (employee == null || !employee.approved) {
            val sendMessage = SendMessage(chatId.toString(), "Вы не являетесь сотрудником")
            absSender.execute(sendMessage)
            return
        }

        val sendMessage = SendMessage().apply { this.chatId = chatId.toString() }

        if (order?.employee != null) {
            sendMessage.text = if (order.employee.telegramId == chatId) {
                "Вы уже работаете над этим заказом"
            } else {
                "Над этим заказом работает другой сотрудник"
            }

            absSender.execute(sendMessage)
            return
        }

        orderClient.setEmployeeToOrder(orderId, chatId)

        val deleteMessage = DeleteMessage(chatId.toString(), callback.callbackQuery.message.messageId)
        absSender.execute(deleteMessage)

        sendMessage.text = "Теперь вы работаете над заказом $orderId"
        absSender.execute(sendMessage)
    }
}