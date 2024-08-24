package com.kirikstreltsov.admin.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid credentials")
class BadCredentialsException : RuntimeException()