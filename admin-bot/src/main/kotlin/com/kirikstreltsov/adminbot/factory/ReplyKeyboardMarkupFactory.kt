package com.kirikstreltsov.adminbot.factory

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object ReplyKeyboardMarkupFactory {
    fun createRoleChoiceKeyboardMarkup(): ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add(KeyboardButton("Покупатель"))
            add(KeyboardButton("Сотрудник"))
        }

        val row2 = KeyboardRow().apply {
            add(KeyboardButton("Админ"))
        }

        val row3 = KeyboardRow().apply {
            add(KeyboardButton("Хочу к вам в команду"))
        }

        return ReplyKeyboardMarkup(listOf(row1, row2, row3))
    }

    fun createCustomerActionChoiceKeyboardMarkup() : ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add(KeyboardButton("Заказать клавиатуру"))
            add(KeyboardButton("Отследить заказ"))
        }

        val row2 = KeyboardRow().apply {
            add(KeyboardButton("Вернуться к выбору роли"))
        }

        return ReplyKeyboardMarkup(listOf(row1, row2))
    }

    fun createEmployeeActionChoiceKeyboardMarkup(): ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add("Получить ссылку на канал для сотрудников")
        }

        val row2 = KeyboardRow().apply {
            add("Вернуться к выбору роли")
        }

        return ReplyKeyboardMarkup(listOf(row1, row2))
    }

    fun createAdminActionChoiceKeyboardMarkup(): ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add(KeyboardButton("Посмотреть список всех заказов"))
            add(KeyboardButton("Отследить заказ"))
        }

        val row2 = KeyboardRow().apply {
            add("Добавить нового админа")
            add("Уволиться")
        }

        val row3 = KeyboardRow().apply {
            add("Получить ссылку на канал для админов")
            add("Компоненты")
        }

        val row4 = KeyboardRow().apply {
            add("Я поменял юзернейм")
        }

        return ReplyKeyboardMarkup(listOf(row1, row2, row3, row4))
    }

    fun createComponentsActionsKeyboardMarkup(): ReplyKeyboardMarkup {
        val row1 = KeyboardRow().apply {
            add("Добавить новый компонент")
            add("Удалить компонент")
        }

        val row2 = KeyboardRow().apply {
            add("Посмотреть все компоненты")
        }

        val row3 = KeyboardRow().apply {
            add("Назад")
        }

        return ReplyKeyboardMarkup(listOf(row1, row2, row3))
    }
}