package com.kirikstreltsov.customersclient

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CustomersWebClientConfiguration {
    private val BASE_URL = "http://localhost:8091"

    @Bean
    @Qualifier("CustomersWebClient")
    fun customersWebClient(): WebClient = WebClient.create(BASE_URL)
}