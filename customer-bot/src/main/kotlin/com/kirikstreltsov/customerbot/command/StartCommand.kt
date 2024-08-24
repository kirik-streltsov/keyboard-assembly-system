package com.kirikstreltsov.customerbot.command

import com.kirikstreltsov.customerbot.annotation.CommandBean
import com.kirikstreltsov.customerbot.chat.Chat
import com.kirikstreltsov.customerbot.chat.ChatService
import com.kirikstreltsov.customerbot.client.CustomerClient
import com.kirikstreltsov.customerbot.factory.ReplyKeyboardMarkupFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class StartCommand(private val client: CustomerClient, private val chatService: ChatService) :
    AbstractCommand() {
    override val alias: String
        get() = "/start"

    override fun execute(update: Update, absSender: AbsSender) {
        val responseCode = client.createCustomer(update.message.chatId, update.message.chat.userName)

        if (responseCode != 200) {
            val sendMessage = SendMessage(update.message.chatId.toString(), "Что то пошло не так")
            absSender.execute(sendMessage)
            return
        }

        val sendMessage = SendMessage
            .builder()
            .chatId(update.message.chatId)
            .text("Привет! Выбери, что хочешь сделать")
            .replyMarkup(ReplyKeyboardMarkupFactory.createCustomerActionChoiceKeyboardMarkup())
            .build()

        absSender.execute(sendMessage)

        chatService.save(Chat(update.message.chatId.toString()))
    }
}