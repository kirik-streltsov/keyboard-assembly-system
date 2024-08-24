package com.kirikstreltsov.customerbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomerBotApplication

fun main(args: Array<String>) {
    runApplication<CustomerBotApplication>(*args)
}
