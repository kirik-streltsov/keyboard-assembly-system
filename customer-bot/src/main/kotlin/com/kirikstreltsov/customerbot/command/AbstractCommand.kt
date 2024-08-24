package com.kirikstreltsov.customerbot.command

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

abstract class AbstractCommand {
    abstract val alias: String
    abstract fun execute(update: Update, absSender: AbsSender)
}