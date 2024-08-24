package com.kirikstreltsov.messagingclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MessagingClientApplication

fun main(args: Array<String>) {
    runApplication<MessagingClientApplication>(*args)
}
