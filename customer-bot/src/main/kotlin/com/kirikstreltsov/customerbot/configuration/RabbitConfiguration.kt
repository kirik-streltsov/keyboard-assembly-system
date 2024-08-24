package com.kirikstreltsov.customerbot.configuration

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
        const val EMPLOYEE_DECLINED_QUEUE = "employee-declined-queue"
        const val ORDER_COMPLETED_QUEUE = "order-completed-queue"
        const val EXCHANGE_NAME = "keyboard-assembly-system"
        const val EMPLOYEE_APPROVE_ROUTING_KEY = "employee-approve"
        const val EMPLOYEE_DECLINED_ROUTING_KEY = "employee-declined"
        const val ORDER_COMPLETED_ROUTING_KEY = "order-completed"
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
    fun employeeDeclinedQueue(): Queue = Queue(EMPLOYEE_DECLINED_QUEUE)

    @Bean
    fun orderCompletedQueue(): Queue = Queue(ORDER_COMPLETED_QUEUE)

    @Bean
    fun directExchange(): DirectExchange = DirectExchange(EXCHANGE_NAME)

    @Bean
    fun employeeApproveBinding(): Binding = BindingBuilder
        .bind(employeeApproveQueue())
        .to(directExchange())
        .with(EMPLOYEE_APPROVE_ROUTING_KEY)

    @Bean
    fun employeeDeclinedBinding(): Binding = BindingBuilder
        .bind(employeeDeclinedQueue())
        .to(directExchange())
        .with(EMPLOYEE_DECLINED_ROUTING_KEY)

    @Bean
    fun orderCompletedBinding(): Binding = BindingBuilder
        .bind(orderCompletedQueue())
        .to(directExchange())
        .with(ORDER_COMPLETED_ROUTING_KEY)

    @Bean
    fun rabbitTemplate(): RabbitTemplate = RabbitTemplate(connectionFactory())
}