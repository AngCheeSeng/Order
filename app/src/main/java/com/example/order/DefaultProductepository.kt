package com.example.order

import com.example.order.database.Product
import com.example.order.database.ProductDao
import com.example.order.database.ProductRepository
import kotlinx.coroutines.flow.Flow

class DefaultProductRepository(private val productDao: ProductDao) : ProductRepository {

    override fun getProduct(id: String): Flow<Product?> {
        return productDao.getProduct(id)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    override fun getProductsByCategory(category: String): Flow<List<Product>> {
        return productDao.getProductsByCategory(category)
    }

    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    override suspend fun insertAll(products: List<Product>) {
        productDao.insertAll(products)
    }

    override suspend fun update(product: Product) {
        productDao.update(product)
    }

    override suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    override suspend fun decreaseProductStock(productId: String, quantity: Int): Int {
        return productDao.decreaseStock(productId, quantity)
    }
}