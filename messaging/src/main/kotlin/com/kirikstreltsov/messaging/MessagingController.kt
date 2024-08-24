package com.kirikstreltsov.messaging

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessagingController(private val messagingService: MessagingService) {
    @PostMapping("/api/v1/messaging/{routingKey}")
    fun sendMessage(@PathVariable routingKey: String, @RequestBody message: String) {
        messagingService.sendMessage(message, routingKey)
    }
}