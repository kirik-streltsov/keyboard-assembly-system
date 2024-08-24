package com.kirikstreltsov.adminbot.session

import org.springframework.stereotype.Service

@Service
class ComponentBuildingSessionService(private val repository: ComponentBuildingSessionRepository) {
    fun createSession(chatId: Long): ComponentBuildingSession {
        return repository.save(ComponentBuildingSession(chatId))
    }

    fun getSession(chatId: Long): ComponentBuildingSession {
        return repository.findById(chatId).orElseThrow()
    }

    fun setComponentType(chatId: Long, componentType: String) {
        val session = repository.findById(chatId).orElseThrow()
        session.componentType = componentType
        repository.save(session)
    }

    fun setComponentName(chatId: Long, componentName: String) {
        val session = repository.findById(chatId).orElseThrow()
        session.componentName = componentName
        repository.save(session)
    }

    fun setComponentPrice(chatId: Long, componentPrice: Double) {
        val session = repository.findById(chatId).orElseThrow()
        session.componentPrice = componentPrice
        repository.save(session)
    }

    fun clearSession(chatId: Long) {
        val session = repository.findById(chatId).orElseThrow()
        repository.delete(session)
    }
}