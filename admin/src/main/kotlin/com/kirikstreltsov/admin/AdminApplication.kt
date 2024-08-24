package com.kirikstreltsov.admin

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EntityScan("com.kirikstreltsov.entities")
class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}

@Bean
fun commandLineRunner(): CommandLineRunner = CommandLineRunner {
    println("Test println")
}