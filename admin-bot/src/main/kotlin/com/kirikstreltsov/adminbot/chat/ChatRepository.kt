package com.kirikstreltsov.adminbot.chat

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : CrudRepository<Chat, String>