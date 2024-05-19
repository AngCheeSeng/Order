@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.order.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.order.R
import com.example.order.database.CartViewModel
import com.example.order.database.Product
import com.example.order.database.ProductDatabase


@Composable
fun Order(navController: NavHostController, category: String, cartViewModel: CartViewModel, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val productDao = ProductDatabase.getDatabase(navController.context).productDao()
    val products by productDao.getProductsByCategory(category).collectAsState(initial = emptyList())

    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = category) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(NavigationDestination.CartView.route) }) {
                        BadgedBox(
                            badge = {
                                Badge(Modifier.size(10.dp))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart View"
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(products) { product ->
                CategoryMenu(
                    product = product,
                    onQuantityChanged = { newQuantity ->
                        if (product.pStock >= newQuantity) {
                            product.pStock -= newQuantity
                        } else {
                            snackbarMessage = "Only ${product.pStock} left in stock"
                        }
                    }
                )
            }

            item {
                Button(
                    onClick = {
                        val selectedProducts = products.filter { it.pQuantity > 0 }
                        selectedProducts.forEach { cartViewModel.addProduct(it) }
                        if (selectedProducts.isNotEmpty()) {
                            navController.navigate(NavigationDestination.OrderMenu.route)
                        } else {
                            Toast.makeText(context, "No products selected. Please add products to the cart.", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Add to Cart")
                }
            }
        }
    }

    snackbarMessage?.let { message ->
        LaunchedEffect(snackbarMessage) {
            Toast.makeText(navController.context, message, Toast.LENGTH_LONG).show()
            snackbarMessage = null
        }
    }
}

@Composable
fun CategoryMenu(
    product: Product,
    onQuantityChanged: (Int) -> Unit
) {
    val counter = remember { mutableIntStateOf(product.pQuantity) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = product.pImageId),
            contentDescription = product.pName,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = product.pName,
                fontSize = 20.sp
            )

            Text(
                text = "RM ${product.pPrice}",
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ValueDecreaseAndIncrease(
            value = counter.intValue,
            onIncrement = {
                counter.intValue++
                product.pQuantity++
                onQuantityChanged(counter.intValue)
            },
            onDecrement = {
                if (counter.intValue > 0) {
                    counter.intValue--
                    product.pQuantity--
                    onQuantityChanged(counter.intValue)
                }
            },
            onValueChanged = { newValue ->
                counter.intValue = newValue
                onQuantityChanged(newValue)
            }
        )
    }
}

@Composable
fun ValueDecreaseAndIncrease(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onValueChanged: (Int) -> Unit
) {
    var count by remember { mutableIntStateOf(value) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            if (count > 0) {
                count--
                onDecrement()
                onValueChanged(count)
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.minus),
                contentDescription = "Decrease",
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = "$count",
            fontSize = 20.sp
        )

        IconButton(onClick = {
            count++
            onIncrement()
            onValueChanged(count)
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}