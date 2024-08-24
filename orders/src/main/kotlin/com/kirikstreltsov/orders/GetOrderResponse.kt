package com.kirikstreltsov.orders

import com.kirikstreltsov.customer.GetCustomerResponse
import com.kirikstreltsov.employees.GetEmployeeResponse
import com.kirikstreltsov.entities.Order
import com.kirikstreltsov.keyboards.GetKeyboardResponse

data class GetOrderResponse(
    val id: Long,
    val keyboard: GetKeyboardResponse,
    val customer: GetCustomerResponse,
    val employee: GetEmployeeResponse?,
    val readinessState: String
) {
    companion object {
        fun fromOrder(order: Order): GetOrderResponse {
            val employeeDto = if (order.employee == null) null
            else GetEmployeeResponse(order.employee!!.telegramId, order.employee!!.username, order.employee!!.approved)

            return GetOrderResponse(
                order.id,
                GetKeyboardResponse.fromKeyboard(order.keyboard),
                GetCustomerResponse.fromCustomer(order.customer),
                employeeDto,
                order.readinessState.value
            )
        }
    }
}