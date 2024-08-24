package com.kirikstreltsov.employees

import com.kirikstreltsov.entities.Employee
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

class EmployeeServiceTests {
    @Mock
    private val repository: EmployeeRepository = mock(EmployeeRepository::class.java)
    private val service = EmployeeService(repository)

    @Test
    fun `should find all employees`() {
        `when`(repository.findAll()).thenReturn(
            listOf(Employee(1, "Kirik"), Employee(2, "Kirik"))
        )

        val employees = service.findAll()

        assertEquals(2, employees.size)
    }

    @Test
    fun `should find employee with id`() {
        val employee = Employee(1, "Kirik")
        `when`(repository.findByTelegramId(employee.telegramId)).thenReturn(employee)

        val found = service.findByTelegramId(employee.telegramId)
        assertEquals(employee, found)
    }

    @Test
    fun `should save employee`() {
        val employee = Employee(1, "Kirik")
        val argumentCaptor = ArgumentCaptor.forClass(Employee::class.java)

        `when`(repository.save(any(Employee::class.java))).thenReturn(employee)

        service.save(employee)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value
        assertEquals(employee, retrieved)
    }

    @Test
    fun `should delete employee`() {
        val employee = Employee(1, "Kirik")

        `when`(repository.findById(employee.telegramId)).thenReturn(Optional.of(employee))

        service.delete(employee.telegramId)
        verify(repository).delete(employee)
    }

    @Test
    fun `should throw ResponseStatusException when trying to delete an unknown employee`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<ResponseStatusException> {
            service.delete(1)
        }
    }

    @Test
    fun `should update employee nickname`() {
        val employee = Employee(1, "Kirik")

        `when`(repository.findById(employee.telegramId)).thenReturn(Optional.of(employee))
        `when`(repository.save(any(Employee::class.java))).thenReturn(employee)

        val newUsername = "@newusername"
        service.changeUsernameById(employee.telegramId, newUsername)

        verify(repository).save(employee)
        assertEquals(newUsername, employee.username)
    }

    @Test
    fun `should throw ResponseStatusException when trying to update an unknown employee`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        val newUsername = "@newusername"
        assertThrows<ResponseStatusException> {
            service.changeUsernameById(1, newUsername)
        }
    }
    
    @Test
    fun `should approve employee`() {
        val employee = Employee(1, "Kirik")
        
        `when`(repository.findById(employee.telegramId)).thenReturn(Optional.of(employee))
        `when`(repository.save(employee)).thenReturn(employee)

        service.approveById(employee.telegramId)

        verify(repository).save(employee)
        assertEquals(true, employee.approved)
    }
    
    @Test
    fun `should throw ResponseStatusException when trying to approve employee`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())
        
        assertThrows<ResponseStatusException> {
            service.approveById(1)
        }
    }
}