package com.kirikstreltsov.customerbot.callback

import com.kirikstreltsov.customerbot.callback.handlers.CallbackHandler
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class CallbackHandlersRepository(context: ApplicationContext) {
    private val handlers = mutableListOf<CallbackHandler>()

    init {
        val beans = context.getBeansOfType(CallbackHandler::class.java).values.map { it as CallbackHandler }
        handlers.addAll(beans)
    }

    fun findMatchingHandler(handlerPattern: String): CallbackHandler? {
        return handlers.find { it.checkPattern(handlerPattern) }
    }
}