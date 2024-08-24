package com.kirikstreltsov.admin

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AdminFilterBeanConfiguration {
    @Bean
    fun requestAdminFilter(adminService: AdminService) : FilterRegistrationBean<AdminRequestFilter> =
        FilterRegistrationBean<AdminRequestFilter>().apply {
            filter = AdminRequestFilter(adminService)
            order = 12
            urlPatterns.add("/api/v1/admin/*")
        }
}