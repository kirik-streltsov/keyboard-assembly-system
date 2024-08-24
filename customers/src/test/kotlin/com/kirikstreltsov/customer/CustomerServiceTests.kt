package com.kirikstreltsov.customer

import com.kirikstreltsov.entities.Customer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

class CustomerServiceTests {
    @Mock
    private val repository: CustomerRepository = mock(CustomerRepository::class.java)
    private val service = CustomerService(repository)

    @Test
    fun `should find all customers`() {
        `when`(repository.findAll()).thenReturn(
            listOf(Customer(1, "Kirik"), Customer(2, "Kirik"))
        )

        val customers = service.findAll()

        assertEquals(2, customers.size)
    }

    @Test
    fun `should find customer with id`() {
        val customer = Customer(1, "Kirik")
        `when`(repository.findById(customer.telegramId)).thenReturn(Optional.of(customer))

        val found = service.findById(customer.telegramId)
        assertEquals(customer, found)
    }

    @Test
    fun `should save customer`() {
        val customer = Customer(1, "Kirik")
        val argumentCaptor = ArgumentCaptor.forClass(Customer::class.java)

        `when`(repository.save(any(Customer::class.java))).thenReturn(customer)

        service.save(customer)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value
        assertEquals(customer, retrieved)
    }

    @Test
    fun `should throw CustomerExistsException when trying to save customer with same id`() {
        val customer = Customer(1, "Kirik")

        `when`(repository.existsByTelegramId(customer.telegramId)).thenReturn(true)

        assertThrows<CustomerExistsException> {
            service.save(customer)
        }
    }

    @Test
    fun `should delete customer`() {
        val customer = Customer(1, "Kirik")

        `when`(repository.findById(customer.telegramId)).thenReturn(Optional.of(customer))

        service.delete(customer.telegramId)
        verify(repository).deleteById(customer.telegramId)
    }

    @Test
    fun `should throw ResponseStatusException when trying to delete an unknown customer`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<ResponseStatusException> {
            service.delete(1)
        }
    }

    @Test
    fun `should update customer nickname`() {
        val customer = Customer(1, "Kirik")

        `when`(repository.findById(customer.telegramId)).thenReturn(Optional.of(customer))
        `when`(repository.save(any(Customer::class.java))).thenReturn(customer)

        val newUsername = "@newusername"
        service.changeUsernameById(customer.telegramId, newUsername)

        verify(repository).save(customer)
        assertEquals(newUsername, customer.username)
    }

    @Test
    fun `should throw ResponseStatusException when trying to update an unknown customer`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        val newUsername = "@newusername"
        assertThrows<ResponseStatusException> {
            service.changeUsernameById(1, newUsername)
        }
    }
}