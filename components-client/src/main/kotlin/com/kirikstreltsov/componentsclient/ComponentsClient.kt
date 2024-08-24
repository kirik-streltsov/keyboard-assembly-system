package com.kirikstreltsov.componentsclient

import com.kirikstreltsov.components.dto.response.GetComponentResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class ComponentsClient(@Qualifier("ComponentsWebClient") private val webClient: WebClient) {
    fun getCaseById(id: Long): GetComponentResponse? {
        return webClient
            .get()
            .uri("/api/v1/components/cases/${id}")
            .retrieve()
            .bodyToMono<GetComponentResponse>()
            .block()
    }

    fun getSwitchById(id: Long): GetComponentResponse? {
        return webClient
            .get()
            .uri("/api/v1/components/cases/${id}").retrieve()
            .bodyToMono<GetComponentResponse>()
            .block()
    }

    fun getKeycapById(id: Long): GetComponentResponse? {
        return webClient
            .get()
            .uri("api/v1/components/keycaps/${id}")
            .retrieve()
            .bodyToMono<GetComponentResponse>()
            .block()
    }
}