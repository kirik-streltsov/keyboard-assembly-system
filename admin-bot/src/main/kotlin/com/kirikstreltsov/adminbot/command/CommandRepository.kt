package com.kirikstreltsov.adminbot.command

import com.kirikstreltsov.adminbot.annotation.CommandBean
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class CommandRepository(context: ApplicationContext) {
    private val commands = mutableListOf<AbstractCommand>()

    init {
        val beans = context.getBeansWithAnnotation(CommandBean::class.java).values.map { it as AbstractCommand }
        commands.addAll(beans)
    }

    fun findCommandsByAlias(alias: String): List<AbstractCommand> = commands.filter { Regex(it.alias).matches(alias) }
}