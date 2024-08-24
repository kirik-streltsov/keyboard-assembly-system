package com.kirikstreltsov.customerbot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.customerbot.client.ComponentsClient
import com.squareup.okhttp.OkHttpClient
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

object InlineKeyboardMarkupFactory {
    private val webClient: OkHttpClient = OkHttpClient()
    private val objectMapper: ObjectMapper = ObjectMapper()
    private val componentsClient = ComponentsClient(webClient, objectMapper)

    fun chooseCaseInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val cases = componentsClient.getAllCases()

        val buttons = cases.map { case ->
            InlineKeyboardButton(case.name).apply {
                callbackData = "case: ${case.id}"
            }
        }

        return InlineKeyboardMarkup(listOf(buttons))
    }

    fun chooseSwitchInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val switches = componentsClient.getAllSwitches()

        val buttons = switches.map { switch ->
            InlineKeyboardButton(switch.name).apply {
                callbackData = "switch: ${switch.id}"
            }
        }

        return InlineKeyboardMarkup(listOf(buttons))
    }

    fun chooseKeycapInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val keycaps = componentsClient.getAllKeycaps()

        val buttons = keycaps.map { keycap ->
            InlineKeyboardButton(keycap.name).apply {
                callbackData = "keycap: ${keycap.id}"
            }
        }

        return InlineKeyboardMarkup(listOf(buttons))
    }

    fun confirmOrderInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val buttons = listOf(
            InlineKeyboardButton("Да").apply { callbackData = "create order" },
            InlineKeyboardButton("Нет").apply { callbackData = "do not create order" }
        )

        return InlineKeyboardMarkup(listOf(buttons))
    }
}