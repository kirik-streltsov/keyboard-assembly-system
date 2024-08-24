package com.kirikstreltsov.customerbot

import com.kirikstreltsov.customerbot.callback.CallbackHandlersRepository
import com.kirikstreltsov.customerbot.command.CommandRepository
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
    private val callbackHandlersRepository: CallbackHandlersRepository
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

        if (update.hasMessage() && update.message.hasText())
            commandRepository.findCommandsByAlias(update.message.text!!).forEach { command ->
                command.execute(update, this)
            }
        else if (update.hasCallbackQuery()) callbackHandlersRepository.findMatchingHandler(update.callbackQuery.data)
            ?.handleCallback(update, this)

    }
}