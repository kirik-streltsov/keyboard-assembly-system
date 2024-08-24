package com.kirikstreltsov.adminbot.update.listeners

import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.client.AdminClient
import com.kirikstreltsov.adminbot.dto.CreateAdminDto
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class AdminForwardedMessageListener(
    private val chatService: ChatService,
    private val adminClient: AdminClient
) : UpdateListener {
    override fun listenUpdate(update: Update, absSender: AbsSender) {
        if (update.message == null)
            return

        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        if (chat.state != Chat.State.AWAITING_FORWARD)
            return

        try {
            val adminId = update.message.forwardFrom.id
            val adminUsername = update.message.forwardFrom.userName
            val admin = CreateAdminDto(adminId, adminUsername)

            adminClient.createAdmin(admin, chat.telegramId.toLong())

            val sendMessage = SendMessage(chat.telegramId, "Админ @${admin.username} успешно добавлен")
            absSender.execute(sendMessage)
        } catch (ex: NullPointerException) {
            val sendMessage = SendMessage(chat.telegramId, "Нужно переслать сообщение другого человека (не себя)")
            absSender.execute(sendMessage)
        } finally {
            chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
        }
    }
}