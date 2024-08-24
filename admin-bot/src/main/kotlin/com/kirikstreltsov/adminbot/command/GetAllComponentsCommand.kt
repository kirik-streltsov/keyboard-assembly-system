package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.client.ComponentsClient
import com.kirikstreltsov.adminbot.formatters.ComponentFormatter
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class GetAllComponentsCommand(
    private val chatService: ChatService,
    private val componentsClient: ComponentsClient,
    private val formatter: ComponentFormatter)
    : AbstractCommand() {
    override val alias: String
        get() = "Посмотреть все компоненты"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        val cases = componentsClient.getAllCases()
        val caseInfo = SendMessage(chat.telegramId, "Кейсы:")
        absSender.execute(caseInfo)

        val sendCases = SendMessage(chat.telegramId, cases.joinToString("\n") { formatter.format(it) })
        absSender.execute(sendCases)

        val switches = componentsClient.getAllSwitches()
        val switchInfo = SendMessage(chat.telegramId, "Свитчи:")
        absSender.execute(switchInfo)

        val sendSwitches = SendMessage(chat.telegramId, switches.joinToString("\n") { formatter.format(it) })
        absSender.execute(sendSwitches)

        val keycaps = componentsClient.getAllKeycaps()
        val keycapInfo = SendMessage(chat.telegramId, "Кейкапы")
        absSender.execute(keycapInfo)

        val sendKeycaps = SendMessage(chat.telegramId, keycaps.joinToString("\n") { formatter.format(it) })
        absSender.execute(sendKeycaps)

        chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
    }
}