package com.kirikstreltsov.admin

import com.kirikstreltsov.admin.exception.BadCredentialsException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.filter.GenericFilterBean

class AdminRequestFilter(private val adminService: AdminService) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, filterChain: FilterChain?) {
        println("AdminRequestFilter Servlet Request Started")

        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val authorization = httpRequest.getHeader("Authorization")
            ?: throw IllegalArgumentException("Authorization header is missing")

        if (!adminService.existsByTelegramId(authorization.toLong())) {
            httpResponse.status = HttpStatus.UNAUTHORIZED.value()
            httpResponse.writer.print("Bad credentials")
            return
        }


        filterChain?.doFilter(request, response)
    }

}