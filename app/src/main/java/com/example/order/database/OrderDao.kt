package com.example.order.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("SELECT * FROM orders WHERE oId = :id")
    fun getOrderById(id: Int): Flow<Order>

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT COUNT(*) FROM orders WHERE pId = :productId")
    fun getOrderCountForProduct(productId: String): Flow<Int>

    @Query("SELECT * FROM orders WHERE customerId = :customerId")
    fun getOrdersByCustomerId(customerId: String): Flow<List<Order>>

}