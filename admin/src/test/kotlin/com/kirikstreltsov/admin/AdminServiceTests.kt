package com.kirikstreltsov.admin

import com.kirikstreltsov.entities.Admin
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.web.server.ResponseStatusException
import java.util.*

class AdminServiceTests {

    @Mock
    var repository: AdminRepository = mock(AdminRepository::class.java)
    var service: AdminService = AdminService(repository)

    @Test
    fun `should exist by telegram id`() {
        Mockito.`when`(repository.existsByTelegramId(anyLong())).thenReturn(true)

        val result = service.existsByTelegramId(1)
        assertTrue(result)
    }

    @Test
    fun `should not exist by telegram id`() {
        Mockito.`when`(repository.existsByTelegramId(anyLong())).thenReturn(false)

        val result = service.existsByTelegramId(1)
        assertFalse(result)
    }

    @Test
    fun `should find admin by telegram id`() {
        val admin = Admin(1, "@nickname")
        Mockito.`when`(repository.findByTelegramId(anyLong())).thenReturn(Optional.of(admin))

        val result = service.findByTelegramId(admin.telegramId)
        assertEquals(result, admin)
    }

    @Test
    fun `should not find admin by telegram id`() {
        val admin = Admin(1, "@nickname")
        Mockito.`when`(repository.findByTelegramId(anyLong())).thenReturn(Optional.empty())

        val result = service.findByTelegramId(admin.telegramId)
        assertNull(result)
    }

    @Test
    fun `should save admin`() {
        val admin = Admin(1, "@nickname")
        val argumentCaptor = ArgumentCaptor.forClass(Admin::class.java)

        Mockito.`when`(repository.save(any(Admin::class.java))).thenReturn(admin)

        service.save(admin)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value
        assertEquals(retrieved, admin)
    }

    @Test
    fun `should find all`() {
        val admin1 = Admin(1, "@nickname1")
        val admin2 = Admin(2, "@nickname2")

        Mockito.`when`(repository.findAll()).thenReturn(listOf(admin1, admin2))

        val result = service.findAll()
        assertEquals(result, listOf(admin1, admin2))
    }

    @Test
    fun `should delete by id`() {
        val admin = Admin(1, "@nickname")

        Mockito.`when`(repository.findByTelegramId(admin.telegramId)).thenReturn(Optional.of(admin))

        service.deleteById(admin.telegramId)
        verify(repository).delete(admin)
    }

    @Test
    fun `should throw ResponseStatusException on deletion`() {
        Mockito.`when`(repository.findByTelegramId(anyLong())).thenReturn(Optional.empty())

        assertThrows(ResponseStatusException::class.java) {
            service.deleteById(1)
        }
    }

    @Test
    fun `should update username`() {
        val admin = Admin(1, "@nickname")
        val newUsername = "@newnickname"

        Mockito.`when`(repository.findByTelegramId(admin.telegramId)).thenReturn(Optional.of(admin))
        Mockito.`when`(repository.save(any(Admin::class.java))).thenReturn(admin)

        service.updateUsernameByTelegramId(admin.telegramId, newUsername)
        assertEquals(newUsername, admin.username)
    }

    @Test
    fun `should throw ResponseStatusException when updating username`() {
        Mockito.`when`(repository.findByTelegramId(anyLong())).thenReturn(Optional.empty())

        assertThrows(ResponseStatusException::class.java) {
            service.updateUsernameByTelegramId(1, "@nickname")
        }
    }
}