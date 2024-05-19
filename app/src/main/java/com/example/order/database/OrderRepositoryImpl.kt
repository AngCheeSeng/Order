package com.example.order.database

import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(private val orderDao: OrderDao) : OrderRepository {
    override suspend fun insertOrder(order: Order) {
        orderDao.insert(order)
    }

    override fun getAllOrders(): Flow<List<Order>> = orderDao.getAllOrders()

    override fun getOrdersByCustomerId(customerId: String): Flow<List<Order>> = orderDao.getOrdersByCustomerId(customerId)

    override suspend fun updateOrder(order: Order) {
        orderDao.update(order)
    }

    override suspend fun deleteOrder(order: Order) {
        orderDao.delete(order)
    }

    override fun getOrderCountForProduct(productId: String): Flow<Int> = orderDao.getOrderCountForProduct(productId)
}