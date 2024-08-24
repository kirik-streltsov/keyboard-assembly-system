package com.kirikstreltsov.adminbot.update.listeners

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class UpdateListenersRepository(private val applicationContext: ApplicationContext) {
    private val listeners = mutableListOf<UpdateListener>()

    init {
        val beans = applicationContext.getBeansOfType(UpdateListener::class.java).values
        listeners.addAll(beans)
    }

    fun getAll(): List<UpdateListener> = listeners
}