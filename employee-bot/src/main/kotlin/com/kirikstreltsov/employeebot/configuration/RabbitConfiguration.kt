package com.kirikstreltsov.employeebot.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfiguration {
    companion object{
        const val EMPLOYEE_APPROVE_QUEUE = "employee-approve"
        const val ORDER_CREATED_QUEUE = "order-created-queue"
        const val EXCHANGE_NAME = "keyboard-assembly-system"
        const val EMPLOYEE_APPROVE_ROUTING_KEY = "employee-approve"
        const val ORDER_CREATED_ROUTING_KEY = "order-created"
    }

    @Value("\${spring.rabbitmq.username}")
    private lateinit var username: String

    @Value("\${spring.rabbitmq.password}")
    private lateinit var password: String

    @Bean
    fun connectionFactory(): ConnectionFactory = CachingConnectionFactory().apply {
        username = this@RabbitConfiguration.username
        setPassword(this@RabbitConfiguration.password)
    }

    @Bean
    fun employeeApproveQueue(): Queue = Queue(EMPLOYEE_APPROVE_QUEUE)

    @Bean
    fun orderCreatedQueue(): Queue = Queue(ORDER_CREATED_QUEUE)

    @Bean
    fun directExchange(): DirectExchange = DirectExchange(EXCHANGE_NAME)

    @Bean
    fun employeeApproveBinding(): Binding = BindingBuilder
        .bind(employeeApproveQueue())
        .to(directExchange())
        .with(EMPLOYEE_APPROVE_ROUTING_KEY)

    @Bean
    fun orderCreatedBinding(): Binding = BindingBuilder
        .bind(orderCreatedQueue())
        .to(directExchange())
        .with(ORDER_CREATED_ROUTING_KEY)

    @Bean
    fun rabbitTemplate(): RabbitTemplate = RabbitTemplate(connectionFactory())
}