package com.kirikstreltsov.components

import com.kirikstreltsov.components.repository.CaseRepository
import com.kirikstreltsov.components.service.CaseService
import com.kirikstreltsov.entities.Case
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import java.util.*

class CaseServiceTests {
    @Mock
    val repository: CaseRepository = mock(CaseRepository::class.java)
    private val service = CaseService(repository)

    @Test
    fun `should find all cases`() {
        val case1 = Case(1, "test1", 99.99)
        val case2 = Case(2, "test2", 99.99)
        `when`(repository.findAll()).thenReturn(
            listOf(case1, case2)
        )

        val list = service.getAllCases()
        assertEquals(2, list.size)
        assertTrue(list.contains(case1))
        assertTrue(list.contains(case2))
    }

    @Test
    fun `should find case by id`() {
        val case = Case(1, "test1", 99.99)

        `when`(repository.findById(case.id)).thenReturn(Optional.of(case))

        assertEquals(case, service.getCaseById(1))
    }

    @Test
    fun `should not find case by id`() {
        val found = service.getCaseById(1)
        assertNull(found)
    }

    @Test
    fun `should save case`() {
        val case = Case(1, "test1", 99.99)
        val argumentCaptor = ArgumentCaptor.forClass(Case::class.java)

        `when`(repository.save(any(Case::class.java))).thenReturn(case)

        service.createCase(case)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value
        assertEquals(case, retrieved)
    }

    @Test
    fun `should update price`() {
        val case = Case(1, "test1", 99.99)

        `when`(repository.findById(case.id)).thenReturn(Optional.of(case))
        `when`(repository.save(any(Case::class.java))).thenReturn(case)

        val newPrice = 199.99
        service.setCasePrice(case.id, newPrice)

        assertEquals(newPrice, case.price)
    }

    @Test
    fun `should throw exception when trying to update price`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            service.setCasePrice(1, 199.99)
        }
    }
}