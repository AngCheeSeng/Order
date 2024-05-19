package com.example.order.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> = _cartProducts

    fun addProduct(product: Product) {
        viewModelScope.launch {
            val currentProducts = _cartProducts.value.toMutableList()
            val existingProduct = currentProducts.find { it.pId == product.pId }
            if (existingProduct != null) {
                existingProduct.pQuantity += product.pQuantity
            } else {
                currentProducts.add(product)
            }
            _cartProducts.value = currentProducts
        }
    }

    fun increaseProductQuantity(product: Product) {
        viewModelScope.launch {
            val currentProducts = _cartProducts.value.toMutableList()
            val existingProduct = currentProducts.find { it.pId == product.pId }
            existingProduct?.let {
                it.pQuantity++
                _cartProducts.value = currentProducts
            }
        }
    }

    fun decreaseProductQuantity(product: Product) {
        viewModelScope.launch {
            val currentProducts = _cartProducts.value.toMutableList()
            val existingProduct = currentProducts.find { it.pId == product.pId }
            existingProduct?.let {
                if (it.pQuantity > 0) it.pQuantity--
                _cartProducts.value = currentProducts
            }
        }
    }

    fun clearCart() {
        _cartProducts.value = emptyList()
    }

    fun removeZeroQuantityProducts() {
        _cartProducts.value = _cartProducts.value.filter { it.pQuantity > 0 }
    }
}
