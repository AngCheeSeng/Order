package com.example.order.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val allProducts: Flow<List<Product>> = repository.getAllProducts()

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return repository.getProductsByCategory(category)
    }
}
