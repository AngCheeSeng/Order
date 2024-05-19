package com.example.order.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    // Function to insert an order
    fun insertOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.insertOrder(order)
        }
    }

    // Function to get all orders
    fun getAllOrders(): Flow<List<Order>> = orderRepository.getAllOrders()

    // Function to get orders by customer ID
    fun getOrdersByCustomerId(customerId: String): Flow<List<Order>> = orderRepository.getOrdersByCustomerId(customerId)

    // Function to update an order
    fun updateOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.updateOrder(order)
        }
    }

    // Function to delete an order
    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.deleteOrder(order)
        }
    }

    // Function to get the total number of orders for a specific product
    fun getOrderCountForProduct(productId: String): Flow<Int> = orderRepository.getOrderCountForProduct(productId)
}
