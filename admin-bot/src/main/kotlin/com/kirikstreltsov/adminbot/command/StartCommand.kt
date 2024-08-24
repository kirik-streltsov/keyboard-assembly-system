package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.factory.AdminRequestFactory
import com.kirikstreltsov.adminbot.factory.ReplyKeyboardMarkupFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class StartCommand(private val client: OkHttpClient, private val chatService: ChatService) :
    AbstractCommand() {
    override val alias: String
        get() = "/start"

    override fun execute(update: Update, absSender: AbsSender) {
        val request = AdminRequestFactory.getAdminByTelegramId(update.message.chatId)
        val response = client.newCall(request).execute()

        val sendMessage: SendMessage = SendMessage().apply { chatId = update.message.chatId.toString() }

        if (!response.isSuccessful)
            sendMessage.apply {
                text = "Вы не админ"
                replyMarkup = ReplyKeyboardMarkupFactory.createRoleChoiceKeyboardMarkup()
            }
        else
            sendMessage.apply {
                text = "Вы успешно авторизовались"
                replyMarkup = ReplyKeyboardMarkupFactory.createAdminActionChoiceKeyboardMarkup()
            }

        chatService.save(Chat(update.message.chatId.toString()))
        absSender.execute(sendMessage)
    }
}