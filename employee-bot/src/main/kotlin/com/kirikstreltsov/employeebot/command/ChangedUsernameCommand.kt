package com.kirikstreltsov.employeebot.command

import com.kirikstreltsov.employeebot.annotation.CommandBean
import com.kirikstreltsov.employeebot.chat.Chat
import com.kirikstreltsov.employeebot.chat.ChatService
import com.kirikstreltsov.employeebot.client.EmployeeClient
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class ChangedUsernameCommand(
    private val chatService: ChatService,
    private val client: EmployeeClient
) : AbstractCommand() {
    override val alias: String
        get() = "Я поменял юзернейм"

    override fun execute(update: Update, absSender: AbsSender) {
        val employeeId = update.message.chatId
        val newUsername = update.message.chat.userName
        val responseCode = client.updateEmployeeUsernameByTelegramId(employeeId, newUsername)

        if (responseCode != 200) {
            val sendMessage = SendMessage(employeeId.toString(), "Что-то пошло не так")
            absSender.execute(sendMessage)
            return
        }

        val sendMessage = SendMessage(employeeId.toString(), "Юзернейм обновлен. Текущий юзернейм: $newUsername")
        absSender.execute(sendMessage)

        chatService.setChatStateById(employeeId.toString(), Chat.State.DEFAULT)
    }
}