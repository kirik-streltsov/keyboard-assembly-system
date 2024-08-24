package com.kirikstreltsov.admin.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
class AdminNotFoundException : RuntimeException()