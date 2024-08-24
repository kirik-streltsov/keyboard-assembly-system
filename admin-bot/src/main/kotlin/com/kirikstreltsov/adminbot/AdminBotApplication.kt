package com.kirikstreltsov.adminbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdminBotApplication

fun main(args: Array<String>) {
    runApplication<AdminBotApplication>(*args)
}
