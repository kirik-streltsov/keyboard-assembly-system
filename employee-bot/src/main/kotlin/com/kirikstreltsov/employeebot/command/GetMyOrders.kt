package com.kirikstreltsov.employeebot.command

import com.kirikstreltsov.employeebot.annotation.CommandBean
import com.kirikstreltsov.employeebot.chat.Chat
import com.kirikstreltsov.employeebot.chat.ChatService
import com.kirikstreltsov.employeebot.client.OrderClient
import com.kirikstreltsov.employeebot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.employeebot.formatters.EmployeeOrderFormatter
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class GetMyOrders(
    private val client: OrderClient,
    private val formatter: EmployeeOrderFormatter,
    private val chatService: ChatService
) : AbstractCommand() {
    override val alias: String
        get() = "Мои заказы"

    override fun execute(update: Update, absSender: AbsSender) {
        val employeeId = update.message.chatId
        val orders = client.getOrdersWithEmployeeId(employeeId)

        orders.forEach {
            val sendMessage = SendMessage(employeeId.toString(), formatter.format(it))
            sendMessage.replyMarkup = InlineKeyboardMarkupFactory.markReadyInlineKeyboardMarkup(it.id)
            absSender.execute(sendMessage)
        }

        chatService.setChatStateById(employeeId.toString(), Chat.State.DEFAULT)
    }
}