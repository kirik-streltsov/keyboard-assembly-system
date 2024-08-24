package com.kirikstreltsov.messagingclient

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class MessagingClient(@Qualifier("MessagingWebClient") private val webClient: WebClient) {
    fun sendMessage(routingKey: String, message: String) {
        webClient
            .post()
            .uri("/api/v1/messaging/$routingKey")
            .body(Mono.just(message), String::class.java)
            .retrieve()
            .toBodilessEntity()
            .block()
    }
}