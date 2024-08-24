package com.kirikstreltsov.admin

import com.kirikstreltsov.entities.Admin
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AdminRepository : JpaRepository<Admin, Long> {
    fun existsByTelegramId(telegramId: Long): Boolean
    fun findByTelegramId(telegramId: Long): Optional<Admin>
}