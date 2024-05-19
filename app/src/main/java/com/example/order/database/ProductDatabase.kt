package com.example.order.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.order.R

@Database(entities = [Product::class], version = 2, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).build().also { INSTANCE = it }
            }
        }

        suspend fun insertSampleProducts(context: Context) {
            val productDao = getDatabase(context).productDao()

            val products = listOf(
                Product("B1", "Teh O Ice", 2.00, 10.99, 0, R.drawable.teh_o, "Beverage", 0),
                Product("B2", "Ice Lemon Tea", 2.00, 10.99, 0, R.drawable.ice_lemon_tea, "Beverage", 2),
                Product("B3", "Cham Ice", 2.00, 10.990, 0, R.drawable.cham_ice, "Beverage", 100),
                Product("B4", "Thai Milk Tea", 2.00, 10.99, 0, R.drawable.thai_milk_tea, "Beverage", 100),
                Product("B5", "3 Layer Tea", 2.00, 10.99,  0, R.drawable.__layer_tea, "Beverage", 100),
                Product("B6", "Kopi Ice", 2.00, 10.99, 0, R.drawable.kopi_ice, "Beverage", 100),
                Product("B7", "Milo Ice", 2.00, 10.99,  0, R.drawable.milo_ice, "Beverage", 100),
                Product("B8", "100 Plus", 2.00, 10.99,  0, R.drawable._00plus, "Beverage", 100),
                Product("B9", "Coca Cola", 2.00, 10.99, 0, R.drawable.cola, "Beverage", 100),
                Product("B10", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Beverage", 100),
                Product("F1", "Soya Bean Milk", 2.00, 10.99, 0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F2", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F3", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F4", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F5", "Soya Bean Milk", 2.00, 10.99, 0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F6", "Soya Bean Milk", 2.00, 10.99, 0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F7", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F8", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F9", "Soya Bean Milk", 2.00, 10.99, 0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("F10", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Breakfast", 100),
                Product("C1", "Soya Bean Milk", 2.00, 10.99,  0, R.drawable.soya_bean_milk, "Chinese Food", 100)
            )

            productDao.insertAll(products)
        }
    }
}

