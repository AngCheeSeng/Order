package com.example.order.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(products: List<Product>)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM Product WHERE pCategory = :category")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE pId = :id")
    fun getProduct(id: String): Flow<Product>

    @Query("SELECT * FROM Product WHERE pStock AND pId = :id")
    fun getProductStock(id: String): Flow<Product>

    @Query("SELECT * FROM Product ORDER BY pName ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("UPDATE Product SET pStock = pStock - :quantity WHERE pId = :productId AND pStock >= :quantity")
    suspend fun decreaseStock(productId: String, quantity: Int): Int


}