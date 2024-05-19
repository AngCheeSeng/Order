package com.example.order.database

import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    // Insert a new order into the database
    suspend fun insertOrder(order: Order)

    // Get all orders from the database
    fun getAllOrders(): Flow<List<Order>>

    // Get orders by customer ID
    fun getOrdersByCustomerId(customerId: String): Flow<List<Order>>

    // Update an existing order in the database
    suspend fun updateOrder(order: Order)

    // Delete an order from the database
    suspend fun deleteOrder(order: Order)

    // Get total number of orders for a specific product
    fun getOrderCountForProduct(productId: String): Flow<Int>
}