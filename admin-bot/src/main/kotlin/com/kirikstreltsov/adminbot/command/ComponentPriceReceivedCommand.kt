package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import com.kirikstreltsov.adminbot.chat.Chat
import com.kirikstreltsov.adminbot.chat.ChatService
import com.kirikstreltsov.adminbot.client.ComponentsClient
import com.kirikstreltsov.adminbot.dto.CreateKeyboardComponentDto
import com.kirikstreltsov.adminbot.dto.GetKeyboardComponentDto
import com.kirikstreltsov.adminbot.factory.ReplyKeyboardMarkupFactory
import com.kirikstreltsov.adminbot.session.ComponentBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@CommandBean
@Component
class ComponentPriceReceivedCommand(
    private val chatService: ChatService,
    private val sessionService: ComponentBuildingSessionService,
    private val componentBuildingSessionService: ComponentBuildingSessionService,
    private val componentsClient: ComponentsClient
) : AbstractCommand() {
    override val alias: String
        get() = "[0-9]+(\\.[0-9]+)?"

    override fun execute(update: Update, absSender: AbsSender) {
        val chat = chatService.findChatByTelegramId(update.message.chatId.toString()) ?: return

        if (chat.state != Chat.State.AWAITING_COMPONENT_PRICE)
            return

        if (update.message.text.endsWith(".")) {
            val sendMessage = SendMessage(chat.telegramId, "Неверный формат цены. Примеры правильного формата: " +
                    "2.99, 30")
            absSender.execute(sendMessage)
            return
        }
        val price = update.message.text.toDouble()

        val session = componentBuildingSessionService.getSession(chat.telegramId.toLong())
        componentBuildingSessionService.setComponentPrice(chat.telegramId.toLong(), price)

        val clientMethod: (CreateKeyboardComponentDto) -> GetKeyboardComponentDto

        when(session.componentType) {
            "case" -> clientMethod = componentsClient::createCase
            "switch" -> clientMethod = componentsClient::createSwitch
            else -> clientMethod = componentsClient::createKeycap
        }

        val name = session.componentName ?: throw RuntimeException("No component name")
        val dto = CreateKeyboardComponentDto(name, price)
        clientMethod.invoke(dto)

        val sendMessage = SendMessage(chat.telegramId, "Компонент успешно создан")
        sendMessage.replyMarkup = ReplyKeyboardMarkupFactory.createAdminActionChoiceKeyboardMarkup()
        absSender.execute(sendMessage)

        sessionService.clearSession(chat.telegramId.toLong())
        chatService.setChatStateById(chat.telegramId, Chat.State.DEFAULT)
    }
}