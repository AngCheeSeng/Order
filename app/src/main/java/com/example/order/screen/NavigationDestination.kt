package com.example.order.screen

sealed class NavigationDestination(val route: String) {
    object OrderMenu : NavigationDestination("orderMenu")
    object Order : NavigationDestination("order")
    object CartView : NavigationDestination("cartView")
    object Bill : NavigationDestination("bill")
}