package com.kirikstreltsov.employeebot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmployeeBotApplication

fun main(args: Array<String>) {
    runApplication<EmployeeBotApplication>(*args)
}
