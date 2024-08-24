package com.kirikstreltsov.customerbot.session

import org.springframework.stereotype.Service

@Service
class KeyboardBuildingSessionService(private val repository: KeyboardBuildingSessionRepository) {
    fun createSession(chatId: Long): KeyboardBuildingSession {
        return repository.save(KeyboardBuildingSession(chatId))
    }

    fun getSession(chatId: Long): KeyboardBuildingSession {
        return repository.findById(chatId).orElseThrow()
    }

    fun setCase(chatId: Long, caseId: Long) {
        val session = repository.findById(chatId).orElseThrow()
        session.caseId = caseId
        repository.save(session)
    }

    fun setSwitch(chatId: Long, switchId: Long) {
        val session = repository.findById(chatId).orElseThrow()
        session.switchId = switchId
        repository.save(session)
    }

    fun setKeycap(chatId: Long, keycapId: Long) {
        val session = repository.findById(chatId).orElseThrow()
        session.keycapId = keycapId
        repository.save(session)
    }

    fun clearSession(chatId: Long) {
        val session = repository.findById(chatId).orElseThrow()
        repository.delete(session)
    }
}