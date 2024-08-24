package com.kirikstreltsov.keyboards

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EntityScan("com.kirikstreltsov.entities")
@ComponentScan("com.kirikstreltsov.componentsclient", "com.kirikstreltsov.keyboards")
class KeyboardsApplication

fun main(args: Array<String>) {
    runApplication<KeyboardsApplication>(*args)
}
