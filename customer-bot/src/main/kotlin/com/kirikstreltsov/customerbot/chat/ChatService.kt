package com.kirikstreltsov.customerbot.chat

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ChatService(private val chatRepository: ChatRepository) {
    fun save(chat: Chat): Chat = chatRepository.save(chat)
    fun findChatByTelegramId(telegramId: String) = chatRepository.findById(telegramId).getOrNull()
    fun setChatStateById(telegramId: String, chatState: String) {
        val chat = chatRepository.findById(telegramId).orElse(null) ?: return
        chat.state = chatState
        chatRepository.save(chat)
    }
}