package com.kirikstreltsov.customerbot.command

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.customerbot.factory.OrderRequestFactory
import com.kirikstreltsov.customerbot.factory.ReplyKeyboardMarkupFactory
import com.kirikstreltsov.customerbot.annotation.CommandBean
import com.kirikstreltsov.customerbot.chat.Chat
import com.kirikstreltsov.customerbot.chat.ChatService
import com.kirikstreltsov.customerbot.dto.Order
import com.kirikstreltsov.customerbot.formatters.KeyboardOrderFormatter
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class EnterOrderIdCommand(
    private val webClient: OkHttpClient,
    private val chatService: ChatService,
    private val objectMapper: ObjectMapper,
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

        val request = OrderRequestFactory.getOrderById(update.message.text.toLong())
        val response = webClient.newCall(request).execute()

        val sendMessage = SendMessage().apply {
            chatId = update.message.chatId.toString()
            replyMarkup = ReplyKeyboardMarkupFactory.createCustomerActionChoiceKeyboardMarkup()
        }

        if (!response.isSuccessful)
            sendMessage.text = "Что-то пошло не так..."

        val body = response.body().string()
        val order = objectMapper.readValue(body, Order::class.java)

        val text = formatter.format(order)

        sendMessage.text = text
        absSender.execute(sendMessage)

        chatService.setChatStateById(update.message.chatId.toString(), Chat.State.DEFAULT)
    }
}