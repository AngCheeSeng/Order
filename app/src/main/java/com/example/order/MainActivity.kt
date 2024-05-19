package com.example.order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.order.database.CartViewModel
import com.example.order.database.OrderViewModel
import com.example.order.database.OrderViewModelFactory
import com.example.order.database.ProductDatabase
import com.example.order.screen.Navigation
import com.example.order.ui.theme.OrderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Insert sample products on app start
        lifecycleScope.launch(Dispatchers.IO) {
            val database = OrderDatabase.getDatabase(this@MainActivity)
            ProductDatabase.insertSampleProducts(this@MainActivity)
        }

        setContent {
            OrderTheme {
                val navController = rememberNavController()
                val cartViewModel = viewModel<CartViewModel>()
                val orderViewModel = viewModel<OrderViewModel>(
                    factory = OrderViewModelFactory(OrderDatabase.getDatabase(applicationContext).orderDao())
                )
                Navigation(navController, cartViewModel, orderViewModel)
            }
        }
    }
}