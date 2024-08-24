package com.kirikstreltsov.orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.kirikstreltsov.customersclient", "com.kirikstreltsov.componentsclient", "com.kirikstreltsov.orders",
    "com.kirikstreltsov.employeesclient", "com.kirikstreltsov.messagingclient")
@EntityScan("com.kirikstreltsov.entities")
class OrdersApplication

fun main(args: Array<String>) {
    runApplication<OrdersApplication>(*args)
}
