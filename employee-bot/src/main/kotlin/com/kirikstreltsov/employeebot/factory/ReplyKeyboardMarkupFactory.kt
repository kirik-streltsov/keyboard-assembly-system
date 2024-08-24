package com.kirikstreltsov.employeebot.factory

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object ReplyKeyboardMarkupFactory {
    fun createEmployeeActionChoiceKeyboardMarkup(): ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add("Свободные заказы")
            add("Отследить заказ по номеру")
        }

        val row2 = KeyboardRow().apply {
            add("Мои заказы")
            add("Уволиться")
        }

        val row3 = KeyboardRow().apply {
            add("Я поменял юзернейм")
        }

        return ReplyKeyboardMarkup(listOf(row1, row2, row3))
    }

    fun createLoginKeyboardMarkup(): ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add("Войти")
        }

        return ReplyKeyboardMarkup(listOf(row1))
    }
}