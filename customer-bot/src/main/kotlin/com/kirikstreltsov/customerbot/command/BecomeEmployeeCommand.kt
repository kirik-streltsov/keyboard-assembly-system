package com.kirikstreltsov.customerbot.command

import com.kirikstreltsov.customerbot.annotation.CommandBean
import com.kirikstreltsov.customerbot.chat.Chat
import com.kirikstreltsov.customerbot.chat.ChatService
import com.kirikstreltsov.customerbot.client.EmployeeClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class BecomeEmployeeCommand(
    private val client: EmployeeClient,
    private val chatService: ChatService
) : AbstractCommand() {
    override val alias: String
        get() = "Хочу к вам в команду"

    override fun execute(update: Update, absSender: AbsSender) {
        val telegramId = update.message.chatId
        val username = update.message.chat.userName

        val employee = client.getEmployeeByTelegramId(telegramId)

        if (employee != null) {
            val sendMessage = SendMessage(telegramId.toString(), "Вы уже являетесь сотрудником")
            absSender.execute(sendMessage)
            return
        }

        val responseCode = client.createEmployee(telegramId, "@${username}")

        if (responseCode != 200) {
            val sendMessage = SendMessage(telegramId.toString(), "Не получилось отправить заявку :(")
            absSender.execute(sendMessage)
            return
        }

        val sendMessage = SendMessage(telegramId.toString(), "Отправили твою заявку. Чуть позже администраторы " +
                "ее проверят и я тебе напишу")
        absSender.execute(sendMessage)

        chatService.setChatStateById(telegramId.toString(), Chat.State.DEFAULT)
    }
}