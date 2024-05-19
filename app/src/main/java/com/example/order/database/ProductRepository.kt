package com.example.order.database

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProduct(id: String): Flow<Product?>

    fun getAllProducts(): Flow<List<Product>>

    fun getProductsByCategory(category: String): Flow<List<Product>>

    suspend fun insert(product: Product)

    suspend fun insertAll(products: List<Product>)

    suspend fun update(product: Product)

    suspend fun delete(product: Product)

    suspend fun decreaseProductStock(productId: String, quantity: Int): Int

}