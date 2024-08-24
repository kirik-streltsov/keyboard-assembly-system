package com.kirikstreltsov.employeebot.chat

import com.kirikstreltsov.employeebot.chat.Chat
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : CrudRepository<Chat, String>