package com.example.order.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.order.R

@Composable
fun OrderMenu(navController: NavHostController) {
    Column {
        // Header with logo and back button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            // Back button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            // Logo
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo), // Your logo resource
                    contentDescription = null,
                    modifier = Modifier
                        .height(64.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
            }

            // Cart button
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.navigate(NavigationDestination.CartView.route)},
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart View"
                    )
                }
            }
        }

        // Menu categories
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            items(listOf("Breakfast", "Chinese Food", "Malaysia Food", "Western", "Snack", "Beverage")) { category ->
                MenuCategoryCard(category) {
                    navController.navigate("${NavigationDestination.Order.route}/$category")
                }
            }
        }
    }
}
@Composable
fun MenuCategoryCard(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = getCategoryImage(category)),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = category,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@DrawableRes
fun getCategoryImage(category: String): Int {
    return when (category) {
        "Breakfast" -> R.drawable.breakfast
        "Chinese Food" -> R.drawable.chinesefood
        "Malaysia Food" -> R.drawable.malaysiafood
        "Western" -> R.drawable.western
        "Snack" -> R.drawable.snack
        "Beverage" -> R.drawable.beverage // Replace with your actual drawable resource IDs
        else -> R.drawable.beverage
    }
}