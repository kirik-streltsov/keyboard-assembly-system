package com.kirikstreltsov.customerbot.chat

import com.kirikstreltsov.customerbot.chat.Chat
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : CrudRepository<Chat, String>