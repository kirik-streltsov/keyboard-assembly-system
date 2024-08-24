package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.ChatService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class GetChannelLinkCommand(
    private val chatService: ChatService,
    @Value("\${channel.inviteLink}")
    private val inviteLink: String) : AbstractCommand() {
    override val alias: String
        get() = "Получить ссылку на канал для админов"

    override fun execute(update: Update, absSender: AbsSender) {
        val sendMessage = SendMessage(update.message.chatId.toString(), inviteLink)
        absSender.execute(sendMessage)
    }
}