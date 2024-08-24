package com.kirikstreltsov.admin

import com.kirikstreltsov.entities.Admin
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AdminService(private val repository: AdminRepository) {
    fun existsByTelegramId(telegramId: Long): Boolean = repository.existsByTelegramId(telegramId)
    fun findByTelegramId(telegramId: Long): Admin? = repository.findByTelegramId(telegramId).orElse(null)
    fun save(admin: Admin) = repository.save(admin)
    fun findAll(): List<Admin> = repository.findAll()
    fun deleteById(id: Long) : Admin {
        val admin = repository.findByTelegramId(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Admin doesn't exist")
        }
        repository.delete(admin)
        return admin
    }
    fun updateUsernameByTelegramId(telegramId: Long, username: String): Admin {
        val admin = repository.findByTelegramId(telegramId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Admin doesn't exist")
        }
        admin.username = username
        return repository.save(admin)
    }
}