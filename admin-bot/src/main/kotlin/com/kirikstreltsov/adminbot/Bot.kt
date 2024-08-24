package com.kirikstreltsov.adminbot

import com.kirikstreltsov.adminbot.callback.CallbackHandlersRepository
import com.kirikstreltsov.adminbot.command.CommandRepository
import com.kirikstreltsov.adminbot.update.listeners.UpdateListenersRepository
import jakarta.annotation.PostConstruct
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
@EnableRabbit
class Bot(
    private val commandRepository: CommandRepository,
    private val callbackHandlersRepository: CallbackHandlersRepository,
    private val updateListenersRepository: UpdateListenersRepository
) : TelegramLongPollingBot() {

    @Value("\${bot.username}")
    lateinit var username: String

    @Value("\${bot.token}")
    lateinit var token: String

    override fun getBotUsername(): String = username

    override fun getBotToken(): String = token

    @PostConstruct
    fun init() {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
    }

    override fun onUpdateReceived(p0: Update?) {
        val update = p0 ?: throw NullPointerException("Update not found")

        updateListenersRepository.getAll().forEach { it.listenUpdate(update, this) }

        if (update.hasMessage() && update.message.hasText())
            commandRepository.findCommandsByAlias(update.message.text!!).forEach { command ->
                command.execute(update, this)
            }
        else if (update.hasCallbackQuery()) callbackHandlersRepository.findMatchingHandler(update.callbackQuery.data)
            ?.handleCallback(update, this)

    }
}