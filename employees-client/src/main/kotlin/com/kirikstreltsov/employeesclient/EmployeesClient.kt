package com.kirikstreltsov.employeesclient

import com.kirikstreltsov.employees.GetEmployeeResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class EmployeesClient(@Qualifier("EmployeesWebClient") private val client: WebClient) {
    fun getEmployeeById(id: Long) = client
        .get()
        .uri("/api/v1/employees/$id")
        .retrieve()
        .bodyToMono<GetEmployeeResponse>()
        .block()
}