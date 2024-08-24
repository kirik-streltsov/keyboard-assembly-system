package com.kirikstreltsov.orders

import com.kirikstreltsov.entities.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.*

class OrderServiceTests {
    @Mock
    private val repository: OrderRepository = Mockito.mock(OrderRepository::class.java)
    private val service = OrderService(repository)

    @Test
    fun `should save order`() {
        val order = Order(
            Keyboard(
                Case(1, "test1", 99.99),
                Switch(1, "test1", 99.99),
                Keycap(1, "test1", 99.99)
            ),
            Customer(1, "@nickname")
        )
        val argumentCaptor = ArgumentCaptor.forClass(Order::class.java)

        `when`(repository.save(order)).thenReturn(order)

        service.save(order)
        verify(repository).save(argumentCaptor.capture())

        val retrieved = argumentCaptor.value

        assertEquals(order, retrieved)
    }

    @Test
    fun `should find all orders`() {
        val list = listOf(
            Order(
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                Customer(1, "@nickname")
            ),
            Order(
                Keyboard(
                    Case(1, "test2", 99.99),
                    Switch(1, "test2", 99.99),
                    Keycap(1, "test2", 99.99)
                ),
                Customer(2, "@nickname")
            )
        )

        `when`(repository.findAll()).thenReturn(list)

        val result = service.findAll()
        assertEquals(list.size, result.size)
        assertEquals(list, result)
    }

    @Test
    fun `should find order by id`() {
        val order = Order(
            1,
            Keyboard(
                Case(1, "test1", 99.99),
                Switch(1, "test1", 99.99),
                Keycap(1, "test1", 99.99)
            ),
            ReadinessState.CREATED,
            Customer(1, "@nickname"),
            null
        )

        `when`(repository.findById(order.id)).thenReturn(Optional.of(order))

        val result = service.findById(order.id)
        assertEquals(order, result)
    }

    @Test
    fun `shouldn't find order by id`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            service.findById(1)
        }
    }

    @Test
    fun `should find orders with employee id`() {
        val employee1 = Employee(1, "@nickname")
        val employee2 = Employee(2, "@nickname")

        val list = listOf(
            Order(
                1,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                employee1
            ),
            Order(
                2,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                employee2
            ),
            Order(
                3,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                null
            ),
            Order(
                4,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                employee1
            )
        )

        `when`(repository.findOrderByEmployeeTelegramId(employee1.telegramId)).thenReturn(list.filter {
            it.employee?.telegramId == employee1.telegramId
        }
        )

        val result = service.findWithEmployeeId(employee1.telegramId)
        assertEquals(2, result.size)
    }

    @Test
    fun `should find vacant orders`() {
        val employee1 = Employee(1, "@nickname")
        val employee2 = Employee(2, "@nickname")

        val list = listOf(
            Order(
                1,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                employee1
            ),
            Order(
                2,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                employee2
            ),
            Order(
                3,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                null
            ),
            Order(
                4,
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                ReadinessState.CREATED,
                Customer(1, "@nickname"),
                employee1
            )
        )

        `when`(repository.findAll()).thenReturn(list)

        val result = service.findVacantOrders()
        assertEquals(1, result.size)
    }

    @Test
    fun `should find orders by customer id`() {
        val customer1 = Customer(1, "@nickname1")
        val customer2 = Customer(2, "@nickname2")

        val list = listOf(
            Order(
                Keyboard(
                    Case(1, "test1", 99.99),
                    Switch(1, "test1", 99.99),
                    Keycap(1, "test1", 99.99)
                ),
                customer1
            ),
            Order(
                Keyboard(
                    Case(1, "test2", 99.99),
                    Switch(1, "test2", 99.99),
                    Keycap(1, "test2", 99.99)
                ),
                customer2
            ),
            Order(
                Keyboard(
                    Case(1, "test2", 99.99),
                    Switch(1, "test2", 99.99),
                    Keycap(1, "test2", 99.99)
                ),
                customer1
            )
        )

        `when`(repository.findAll()).thenReturn(list)

        val result = service.findOrdersByCustomerId(customer1.telegramId)
        assertEquals(2, result.size)
    }

    @Test
    fun `should update employee id in order`() {
        val employee1 = Employee(1, "@nickname1")
        val employee2 = Employee(2, "@nickname2")

        val order = Order(
            1,
            Keyboard(
                Case(1, "test1", 99.99),
                Switch(1, "test1", 99.99),
                Keycap(1, "test1", 99.99)
            ),
            ReadinessState.CREATED,
            Customer(1, "@nickname"),
            employee1
        )

        `when`(repository.findById(order.id)).thenReturn(Optional.of(order))

        val result = service.setEmployeeById(order.id, employee2)
        assertEquals(employee2, result.employee)
    }

    @Test
    fun `should throw NoSuchElementException when trying to update employee`() {
        val employee = Employee(1, "@nickname1")

        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            service.setEmployeeById(1, employee)
        }
    }

    @Test
    fun `should update readiness state`() {
        val employee = Employee(1, "@nickname1")
        val order = Order(
            1,
            Keyboard(
                Case(1, "test1", 99.99),
                Switch(1, "test1", 99.99),
                Keycap(1, "test1", 99.99)
            ),
            ReadinessState.CREATED,
            Customer(1, "@nickname"),
            employee
        )

        `when`(repository.findById(order.id)).thenReturn(Optional.of(order))

        val result = service.setReadinessStateById(order.id, ReadinessState.COMPLETED.name)
        assertEquals(ReadinessState.COMPLETED, result.readinessState)
    }

    @Test
    fun `should throw NoSuchElementException when trying to update readiness state`() {
        `when`(repository.findById(anyLong())).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            service.setReadinessStateById(1, ReadinessState.COMPLETED.name)
        }
    }
}