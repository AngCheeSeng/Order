package com.example.order.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val oId: Int = 0,
    val pId: String,
    val oPrice: Double = 0.00,
    val oQuantity: Int = 0,
    val oTable: String = "",
    val customerId: String = ""
)
