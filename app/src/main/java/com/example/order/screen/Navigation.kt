package com.example.order.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.order.database.CartViewModel
import com.example.order.database.OrderViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.OrderMenu.route
    ) {
        composable(NavigationDestination.OrderMenu.route) {
            OrderMenu(navController)
        }
        composable("${NavigationDestination.Order.route}/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            Order(navController, category, cartViewModel)
        }
        composable(NavigationDestination.CartView.route) {
            CartView(navController, cartViewModel)
        }
        composable(NavigationDestination.Bill.route) {
            Bill(cartViewModel, navController, orderViewModel)
        }
    }
}



