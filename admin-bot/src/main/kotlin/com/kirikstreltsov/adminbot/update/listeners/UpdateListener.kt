package com.kirikstreltsov.adminbot.update.listeners

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

interface UpdateListener {
    fun listenUpdate(update: Update, absSender: AbsSender)
}