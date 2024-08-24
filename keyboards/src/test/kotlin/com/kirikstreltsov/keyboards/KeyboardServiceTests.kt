package com.kirikstreltsov.keyboards

import com.kirikstreltsov.entities.Case
import com.kirikstreltsov.entities.Keyboard
import com.kirikstreltsov.entities.Keycap
import com.kirikstreltsov.entities.Switch
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.mockito.*
import org.mockito.Mockito.*
import java.util.*

class KeyboardServiceTests {
    @Mock
    private val repository = mock(KeyboardRepository::class.java)
    private val service = KeyboardService(repository)

    @Test
    fun `should find all keyboards`() {
        val keyboard1 = Keyboard(
            Case(1, "test1", 99.99),
            Switch(1, "test1", 99.99),
            Keycap(1, "test1", 99.99)
        )
        val keyboard2 = Keyboard(
            Case(2, "test2", 99.99),
            Switch(2, "test2", 99.99),
            Keycap(2, "test2", 99.99)
        )

        `when`(repository.findAll()).thenReturn(listOf(keyboard1, keyboard2))

        val list = service.findAll()

        assertEquals(listOf(keyboard1, keyboard2), list)
    }

    @Test
    fun `should save keyboard`() {
        val keyboard = Keyboard(
            Case(1, "test1", 99.99),
            Switch(1, "test1", 99.99),
            Keycap(1, "test1", 99.99)
        )
        val argumentCaptor = ArgumentCaptor.forClass(Keyboard::class.java)

        `when`(repository.save(keyboard)).thenReturn(keyboard)

        service.saveKeyboard(keyboard)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value

        assertEquals(keyboard, retrieved)
    }

    @Test
    fun `should find keyboard by id`() {
        val keyboard = Keyboard(
            1,
            Case(1, "test1", 99.99),
            Switch(1, "test1", 99.99),
            Keycap(1, "test1", 99.99)
        )

        `when`(repository.findById(keyboard.id)).thenReturn(Optional.of(keyboard))

        val found = service.findById(keyboard.id)
        assertEquals(keyboard, found)
    }

    @Test
    fun `should not find keyboard by id and return null`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        val found = service.findById(1)
        assertNull(found)
    }
}