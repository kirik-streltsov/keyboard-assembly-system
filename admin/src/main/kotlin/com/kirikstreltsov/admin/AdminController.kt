package com.kirikstreltsov.admin

import com.kirikstreltsov.entities.Admin
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/admin")
class AdminController(private val adminService: AdminService) {
    @GetMapping
    fun findAll(): List<GetAdminResponse> = adminService.findAll()
        .map { GetAdminResponse(it.telegramId, it.username) }

    @GetMapping("/{telegramId}")
    fun getAdminByTelegramId(@PathVariable telegramId: Long): GetAdminResponse? {
        val admin = adminService.findByTelegramId(telegramId) ?: return null
        return GetAdminResponse(admin.telegramId, admin.username)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAdmin(@RequestBody dto: CreateAdminRequest): GetAdminResponse {
        val admin = Admin(dto.telegramId, dto.username)
        adminService.save(admin)
        return GetAdminResponse(admin.telegramId, admin.username)
    }

    @PatchMapping("/{id}/username")
    fun changeUsernameById(@PathVariable id: Long, dto: ChangeAdminUsernameRequest): GetAdminResponse {
        val admin = adminService.updateUsernameByTelegramId(id, dto.username)
        return GetAdminResponse(admin.telegramId, admin.username)
    }

    @DeleteMapping("/{telegramId}")
    fun deleteAdmin(@PathVariable("telegramId") telegramId: Long): GetAdminResponse {
        val admin = adminService.findByTelegramId(telegramId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        adminService.deleteById(admin.telegramId)
        return GetAdminResponse(admin.telegramId, admin.username)
    }
}