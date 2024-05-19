@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.order.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@SuppressLint("UnrememberedMutableState")
@Composable
fun CartView(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    val products by cartViewModel.cartProducts.collectAsState()
    val totalPrice by derivedStateOf {
        products.sumOf { it.pQuantity * it.pPrice }
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val decimalFormat = DecimalFormat("#.##")
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .height(40.dp)
                                .aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Cart", fontSize = 20.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(products) { product ->
                    ProductRow(
                        product = product,
                        onIncrement = { cartViewModel.increaseProductQuantity(product) },
                        onDecrement = { cartViewModel.decreaseProductQuantity(product) }
                    )
                }
            }

            // Total price
            Text(
                text = "Total Price: RM ${decimalFormat.format(totalPrice)}",
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp
            )

            Button(
                onClick = {
                    if (products.any { it.pQuantity > 0 }) {

                        scope.launch {
                            // Remove products with zero quantity
                            cartViewModel.removeZeroQuantityProducts()
                            // Navigate to Bill screen
                            navController.navigate(NavigationDestination.Bill.route)
                        }
                    } else {
                        Toast.makeText(context, "Cart is empty. Please add products to the cart.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Submit Order")
            }
        }
    }
}

@Composable
fun ProductRow(
    product: Product,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = product.pName,
                fontSize = 20.sp
            )

            Text(
                text = "$${product.pPrice}",
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ValueDecreaseAndIncreaseCart(
            value = product.pQuantity,
            onIncrement = onIncrement,
            onDecrement = onDecrement,
            onValueChanged = { newValue ->
                product.pQuantity = newValue
            }
        )
    }
}

@Composable
fun ValueDecreaseAndIncreaseCart(
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