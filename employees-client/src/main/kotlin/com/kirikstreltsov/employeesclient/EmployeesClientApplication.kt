package com.kirikstreltsov.employeesclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmployeesClientApplication

fun main(args: Array<String>) {
    runApplication<EmployeesClientApplication>(*args)
}
