package com.kirikstreltsov.customerbot.factory

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object ReplyKeyboardMarkupFactory {
    fun createCustomerActionChoiceKeyboardMarkup(): ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add(KeyboardButton("Заказать клавиатуру"))
            add(KeyboardButton("Отследить заказ"))
        }

        val row2 = KeyboardRow().apply {
            add("Хочу к вам в команду")
        }

        val row3 = KeyboardRow().apply {
            add("Я поменял юзернейм")
        }

        return ReplyKeyboardMarkup(listOf(row1, row2, row3))
    }
}