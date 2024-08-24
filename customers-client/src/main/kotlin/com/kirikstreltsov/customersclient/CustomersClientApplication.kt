package com.kirikstreltsov.customersclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomersClientApplication

fun main(args: Array<String>) {
    runApplication<CustomersClientApplication>(*args)
}
