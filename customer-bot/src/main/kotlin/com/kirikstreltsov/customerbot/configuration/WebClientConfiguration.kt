package com.kirikstreltsov.customerbot.configuration

import com.squareup.okhttp.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebClientConfiguration {
    @Bean
    fun webClient() : OkHttpClient = OkHttpClient()
}