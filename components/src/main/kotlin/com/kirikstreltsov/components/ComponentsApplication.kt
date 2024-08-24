package com.kirikstreltsov.components

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan("com.kirikstreltsov.entities")
class ComponentsApplication

fun main(args: Array<String>) {
    runApplication<ComponentsApplication>(*args)
}
