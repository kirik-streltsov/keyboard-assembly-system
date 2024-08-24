package com.kirikstreltsov.componentsclient

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ComponentsWebClientConfiguration {
    private val BASE_URL = "http://localhost:8080"

    @Bean
    @Qualifier("ComponentsWebClient")
    fun componentsWebClient(): WebClient = WebClient
            .builder()
            .baseUrl(BASE_URL)
            .build()
}