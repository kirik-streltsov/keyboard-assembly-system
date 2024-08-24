package com.kirikstreltsov.adminbot.callback.handlers

import com.kirikstreltsov.adminbot.client.OrderClient
import com.kirikstreltsov.adminbot.dto.CreateKeyboardDto
import com.kirikstreltsov.adminbot.dto.CreateOrderDto
import com.kirikstreltsov.adminbot.formatters.OrderCreatedFormatter
import com.kirikstreltsov.adminbot.session.KeyboardBuildingSessionService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateOrderCallbackHandler(
    private val sessionService: KeyboardBuildingSessionService,
    private val orderClient: OrderClient,
    private val formatter: OrderCreatedFormatter
) : CallbackHandler {
    override fun checkPattern(pattern: String): Boolean {
        return pattern == "create order"
    }

    override fun handleCallback(callback: Update, absSender: AbsSender) {
        val chatId = callback.callbackQuery.message.chatId
        val previousMessageId = callback.callbackQuery.message.messageId

        val session = sessionService.getSession(chatId)
        val caseId = session.caseId ?: throw RuntimeException("case not found")
        val switchId = session.switchId ?: throw RuntimeException("switch not found")
        val keycapId = session.keycapId ?: throw RuntimeException("keycap not found")

        val keyboard = CreateKeyboardDto(caseId, switchId, keycapId)
        val createOrderDto = CreateOrderDto(keyboard, chatId)
        val order = orderClient.createOrder(createOrderDto)

        sessionService.clearSession(chatId)

        val sendMessage = SendMessage(chatId.toString(), formatter.format(order))
        absSender.execute(sendMessage)

        val deleteMessage = DeleteMessage(chatId.toString(), previousMessageId)
        absSender.execute(deleteMessage)
    }
}