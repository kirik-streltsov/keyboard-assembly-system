package com.kirikstreltsov.messagingclient

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MessagingClientConfiguration {
    private val BASE_URL = "http://localhost:8095"

    @Bean
    @Qualifier("MessagingWebClient")
    fun webClient(): WebClient = WebClient.create(BASE_URL)
}