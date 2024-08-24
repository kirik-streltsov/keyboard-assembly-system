package com.kirikstreltsov.adminbot.command

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.dto.Order
import com.kirikstreltsov.adminbot.factory.OrderRequestFactory
import com.kirikstreltsov.adminbot.formatters.AdminOrderFormatter
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class GetAllOrdersCommand(
    private val webClient: OkHttpClient,
    private val chatService: ChatService,
    private val objectMapper: ObjectMapper,
    private val formatter: AdminOrderFormatter
) : AbstractCommand() {
    override val alias: String
        get() = "Посмотреть список всех заказов"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        val request = OrderRequestFactory.getAllOrders()
        val response = webClient.newCall(request).execute()

        val orderList: List<Order> =
            objectMapper.readValue(response.body()!!.string(), object : TypeReference<List<Order>>() {})

        orderList.map { formatter.format(it) }.forEach { text ->
            val sendMessage = SendMessage(update.message.chatId.toString(), text)
            absSender.execute(sendMessage)
        }

        chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
    }
}