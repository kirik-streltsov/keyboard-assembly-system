package com.kirikstreltsov.employeebot.command

import com.kirikstreltsov.employeebot.annotation.CommandBean
import com.kirikstreltsov.employeebot.chat.Chat
import com.kirikstreltsov.employeebot.chat.ChatService
import com.kirikstreltsov.employeebot.client.OrderClient
import com.kirikstreltsov.employeebot.dto.Order
import com.kirikstreltsov.employeebot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.employeebot.formatters.KeyboardOrderFormatter
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class EnterOrderIdCommand(
    private val chatService: ChatService,
    private val orderClient: OrderClient,
    private val formatter: KeyboardOrderFormatter
) : AbstractCommand() {
    override val alias: String
        get() = "[0-9]+"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: throw NullPointerException()

        if (chat.state != Chat.State.AWAITING_ORDER_ID) {
            println("not enter id")
            return
        }

        val orderId = update.message.text.toLong()
        val order = orderClient.getOrderById(orderId)

        val sendMessage = SendMessage().apply {
            chatId = update.message.chatId.toString()
        }

        if (order == null) {
            sendMessage.text = "Что-то пошло не так..."
            absSender.execute(sendMessage)
            return
        }

        val text = formatter.format(order)
        sendMessage.text = text

        if (order.employee == null) {
            sendMessage.replyMarkup = InlineKeyboardMarkupFactory.orderAcceptInlineKeyboardMarkup(orderId)
        }

        absSender.execute(sendMessage)

        chatService.setChatStateById(update.message.chatId.toString(), Chat.State.DEFAULT)
    }
}