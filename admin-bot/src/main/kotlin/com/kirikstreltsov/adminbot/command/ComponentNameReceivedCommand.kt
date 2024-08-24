package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.session.ComponentBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class ComponentNameReceivedCommand(
    private val chatService: ChatService,
    private val componentBuildingSessionService: ComponentBuildingSessionService
) : AbstractCommand() {
    override val alias: String
        get() = ".*"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        if (chat.state != Chat.State.AWAITING_COMPONENT_NAME)
            return

        val name = update.message.text
        componentBuildingSessionService.setComponentName(chat.telegramId.toLong(), name)

        val sendMessage = SendMessage(chat.telegramId, "Пришлите цену за компонент")
        absSender.execute(sendMessage)

        chatService.setChatStateById(chat.telegramId, Chat.State.AWAITING_COMPONENT_PRICE)
    }
}