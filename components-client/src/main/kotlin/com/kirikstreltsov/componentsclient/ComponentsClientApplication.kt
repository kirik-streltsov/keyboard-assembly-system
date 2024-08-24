package com.kirikstreltsov.componentsclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ComponentsClientApplication

fun main(args: Array<String>) {
    runApplication<ComponentsClientApplication>(*args)
}
