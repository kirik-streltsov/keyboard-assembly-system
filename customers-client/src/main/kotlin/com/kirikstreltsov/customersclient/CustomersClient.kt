package com.kirikstreltsov.customersclient

import com.kirikstreltsov.customer.GetCustomerResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class CustomersClient(@Qualifier("CustomersWebClient") val webClient: WebClient) {
    fun getCustomerById(id: Long): GetCustomerResponse? {
        return webClient
            .get()
            .uri("/api/v1/customers/${id}")
            .retrieve()
            .bodyToMono<GetCustomerResponse>()
            .block()
    }
}