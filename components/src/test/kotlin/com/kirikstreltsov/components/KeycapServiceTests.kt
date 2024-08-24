package com.kirikstreltsov.components

import com.kirikstreltsov.components.repository.KeycapRepository
import com.kirikstreltsov.components.service.KeycapService
import com.kirikstreltsov.entities.Keycap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import java.util.*

class KeycapServiceTests {
    @Mock
    val repository: KeycapRepository = mock(KeycapRepository::class.java)
    private val service = KeycapService(repository)

    @Test
    fun `should find all keycaps`() {
        val keycap1 = Keycap(1, "test1", 99.99)
        val keycap2 = Keycap(2, "test2", 99.99)
        `when`(repository.findAll()).thenReturn(
            listOf(keycap1, keycap2)
        )

        val list = service.findAllKeycaps()
        assertEquals(2, list.size)
        assertTrue(list.contains(keycap1))
        assertTrue(list.contains(keycap2))
    }

    @Test
    fun `should find keycap by id`() {
        val keycap = Keycap(1, "test1", 99.99)

        `when`(repository.findById(keycap.id)).thenReturn(Optional.of(keycap))

        assertEquals(keycap, service.findKeycapById(1))
    }

    @Test
    fun `should not find keycap by id`() {
        val found = service.findKeycapById(1)
        assertNull(found)
    }

    @Test
    fun `should save keycap`() {
        val keycap = Keycap(1, "test1", 99.99)
        val argumentCaptor = ArgumentCaptor.forClass(Keycap::class.java)

        `when`(repository.save(any(Keycap::class.java))).thenReturn(keycap)

        service.saveKeycap( keycap)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value
        assertEquals(keycap, retrieved)
    }

    @Test
    fun `should update price`() {
        val keycap = Keycap(1, "test1", 99.99)

        `when`(repository.findById(keycap.id)).thenReturn(Optional.of(keycap))
        `when`(repository.save(any(Keycap::class.java))).thenReturn(keycap)

        val newPrice = 199.99
        service.setPriceById(keycap.id, newPrice)

        assertEquals(newPrice, keycap.price)
    }

    @Test
    fun `should throw exception when trying to update price`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<RuntimeException> {
            service.setPriceById(1, 199.99)
        }
    }
}