package com.kirikstreltsov.employeebot.factory

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

object InlineKeyboardMarkupFactory {

    fun orderAcceptInlineKeyboardMarkup(orderId: Long): InlineKeyboardMarkup {
        val buttons = listOf(
            InlineKeyboardButton("Принять заказ").apply {
                callbackData = "accept order: $orderId"
            }
        )

        return InlineKeyboardMarkup(listOf(buttons))
    }

    fun markReadyInlineKeyboardMarkup(orderId: Long): InlineKeyboardMarkup {
        val buttons = listOf(
            InlineKeyboardButton("Пометить как готовый").apply {
                callbackData = "ready order: $orderId"
            }
        )

        return InlineKeyboardMarkup(listOf(buttons))
    }
}