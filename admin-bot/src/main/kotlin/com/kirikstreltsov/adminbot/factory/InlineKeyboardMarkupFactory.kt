package com.kirikstreltsov.adminbot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.client.ComponentsClient
import com.kirikstreltsov.adminbot.dto.GetKeyboardComponentDto
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

    fun createComponentTypesInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val row1 = listOf(
            InlineKeyboardButton("Кейс").apply { callbackData = "create-component: case" },
            InlineKeyboardButton("Свитчи").apply { callbackData = "create-component: switch" },
        )

        val row2 = listOf(
            InlineKeyboardButton("Кейкап").apply { callbackData = "create-component: keycap" }
        )

        return InlineKeyboardMarkup(listOf(row1, row2))
    }

    fun removeComponentTypesInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val row1 = listOf(
            InlineKeyboardButton("Кейс").apply { callbackData = "remove-component-type: case" },
            InlineKeyboardButton("Свитчи").apply { callbackData = "remove-component-type: switch" },
        )

        val row2 = listOf(
            InlineKeyboardButton("Кейкап").apply { callbackData = "remove-component-type: keycap" }
        )

        return InlineKeyboardMarkup(listOf(row1, row2))
    }

    fun removeComponentChoiceInlineKeyboardMarkup(
        components: List<GetKeyboardComponentDto>,
        componentType: String
    ): InlineKeyboardMarkup {
        val buttons = components.map {
            InlineKeyboardButton(it.name).apply {
                callbackData = "remove-${componentType}-id: ${it.id}"
            }
        }

        return InlineKeyboardMarkup(listOf(buttons))
    }

    fun acceptEmployeeInlineKeyboardMarkup(employeeId: Long): InlineKeyboardMarkup {
        val buttons = listOf(
            InlineKeyboardButton("✅").apply { callbackData = "accept-employee: $employeeId" },
            InlineKeyboardButton("❌").apply { callbackData = "decline-employee: $employeeId" },
        )

        return InlineKeyboardMarkup(listOf(buttons))
    }
}