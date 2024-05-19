package com.example.order.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Product"
)
data class Product(
    @PrimaryKey(autoGenerate = false)
    val pId: String,
    val pName: String,
    val pBuyPrice: Double,
    val pPrice: Double,
    var pQuantity: Int,
    val pImageId: Int,
    val pCategory: String,
    var pStock: Int
)


