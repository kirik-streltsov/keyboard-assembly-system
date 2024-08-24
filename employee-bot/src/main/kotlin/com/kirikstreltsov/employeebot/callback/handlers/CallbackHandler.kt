package com.kirikstreltsov.employeebot.callback.handlers

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

interface CallbackHandler {
    fun checkPattern(pattern: String): Boolean
    fun handleCallback(callback: Update, absSender: AbsSender)
}