package com.kirikstreltsov.components

import com.kirikstreltsov.components.repository.SwitchRepository
import com.kirikstreltsov.components.service.SwitchService
import com.kirikstreltsov.entities.Switch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import java.util.*

class SwitchServiceTests {
    @Mock
    val repository: SwitchRepository = mock(SwitchRepository::class.java)
    private val service = SwitchService(repository)

    @Test
    fun `should find all switches`() {
        val switch1 = Switch(1, "test1", 99.99)
        val switch2 = Switch(2, "test2", 99.99)
        `when`(repository.findAll()).thenReturn(
            listOf(switch1, switch2)
        )

        val list = service.getAllSwitches()
        assertEquals(2, list.size)
        assertTrue(list.contains(switch1))
        assertTrue(list.contains(switch2))
    }

    @Test
    fun `should find switch by id`() {
        val switch = Switch(1, "test1", 99.99)

        `when`(repository.findById(switch.id)).thenReturn(Optional.of(switch))

        assertEquals(switch, service.getSwitchById(1))
    }

    @Test
    fun `should not find switch by id`() {
        val found = service.getSwitchById(1)
        assertNull(found)
    }

    @Test
    fun `should save switch`() {
        val switch = Switch(1, "test1", 99.99)
        val argumentCaptor = ArgumentCaptor.forClass(Switch::class.java)

        `when`(repository.save(any(Switch::class.java))).thenReturn(switch)

        service.saveSwitch(switch)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value
        assertEquals(switch, retrieved)
    }

    @Test
    fun `should update price`() {
        val switch = Switch(1, "test1", 99.99)

        `when`(repository.findById(switch.id)).thenReturn(Optional.of(switch))
        `when`(repository.save(any(Switch::class.java))).thenReturn(switch)

        val newPrice = 199.99
        service.setSwitchPrice(switch.id, newPrice)

        assertEquals(newPrice, switch.price)
    }

    @Test
    fun `should throw exception when trying to update price`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<RuntimeException> {
            service.setSwitchPrice(1, 199.99)
        }
    }
}