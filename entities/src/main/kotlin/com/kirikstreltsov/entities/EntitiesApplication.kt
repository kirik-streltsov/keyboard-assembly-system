package com.kirikstreltsov.entities

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EntitiesApplication

fun main(args: Array<String>) {
    runApplication<EntitiesApplication>(*args)
    println("Up and running :)")
}
