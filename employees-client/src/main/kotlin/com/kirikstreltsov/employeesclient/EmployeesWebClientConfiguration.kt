package com.kirikstreltsov.employeesclient

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class EmployeesWebClientConfiguration {
    private val BASE_URL = "http://localhost:8093"

    @Bean
    @Qualifier("EmployeesWebClient")
    fun employeesWebClient(): WebClient = WebClient.create(BASE_URL)
}