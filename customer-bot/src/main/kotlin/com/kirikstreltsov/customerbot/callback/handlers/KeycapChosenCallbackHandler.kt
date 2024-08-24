package com.kirikstreltsov.customerbot.callback.handlers

import com.kirikstreltsov.customerbot.client.ComponentsClient
import com.kirikstreltsov.customerbot.dto.Keyboard
import com.kirikstreltsov.customerbot.factory.InlineKeyboardMarkupFactory
import com.kirikstreltsov.customerbot.formatters.KeyboardCreatedFormatter
import com.kirikstreltsov.customerbot.session.KeyboardBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class KeycapChosenCallbackHandler(
    private val sessionService: KeyboardBuildingSessionService,
    private val componentsClient: ComponentsClient,
    private val formatter: KeyboardCreatedFormatter
) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern.startsWith("keycap: ")
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val keycapId = callback.callbackQuery.data.split(" ")[1].toLong()
        val previousMessageId = callback.callbackQuery.message.messageId

        val chatId = callback.callbackQuery.message.chatId
        sessionService.setKeycap(chatId, keycapId)

        val session = sessionService.getSession(chatId)
        val caseId = session.caseId
        val switchId = session.switchId

        val case = componentsClient.getCaseById(caseId!!) ?: throw RuntimeException("Case $caseId not found")
        val switch = componentsClient.getSwitchById(switchId!!) ?: throw RuntimeException("Switch $switchId not found")
        val keycap = componentsClient.getKeycapById(keycapId) ?: throw RuntimeException("Keycap $keycapId not found")

        val keyboard = Keyboard(case, switch, keycap)

        val sendMessage = SendMessage(chatId.toString(), formatter.format(keyboard))
        sendMessage.replyMarkup = InlineKeyboardMarkupFactory.confirmOrderInlineKeyboardMarkup()
        absSender.execute(sendMessage)

        val deleteMessage = DeleteMessage(chatId.toString(), previousMessageId)
        absSender.execute(deleteMessage)
    }
}