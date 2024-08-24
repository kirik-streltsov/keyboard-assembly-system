package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.client.AdminClient
import com.kirikstreltsov.adminbot.factory.ReplyKeyboardMarkupFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class FireAdminCommand(private val chatService: ChatService, private val adminClient: AdminClient) : AbstractCommand() {
    override val alias: String
        get() = "Уволиться"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        adminClient.fireAdmin(chat.telegramId.toLong(), chat.telegramId.toLong())

        val sendMessage = SendMessage(chat.telegramId, "Вы успешно уволились")
        sendMessage.replyMarkup = ReplyKeyboardMarkupFactory.createRoleChoiceKeyboardMarkup()

        absSender.execute(sendMessage)
        chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
    }
}