package com.kirikstreltsov.employees

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EntityScan("com.kirikstreltsov.entities")
@ComponentScan("com.kirikstreltsov.messagingclient", "com.kirikstreltsov.employees")
class EmployeesApplication

fun main(args: Array<String>) {
    runApplication<EmployeesApplication>(*args)
}
