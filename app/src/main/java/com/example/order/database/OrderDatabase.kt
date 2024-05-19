import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.order.database.Order
import com.example.order.database.OrderDao
import com.example.order.database.Product
import com.example.order.database.ProductDao

@Database(entities = [Product::class, Order::class], version = 1,exportSchema = false)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getDatabase(context: Context): OrderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "order_database"  // Updated to reflect the purpose
                ).fallbackToDestructiveMigration()  // Optional: handles migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}