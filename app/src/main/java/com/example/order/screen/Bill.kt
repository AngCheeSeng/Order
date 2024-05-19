package com.example.order.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.order.R
import com.example.order.database.CartViewModel
import com.example.order.database.OrderViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun Bill(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    orderViewModel: OrderViewModel,
    //customerId: String, // Customer ID
    //tableNumber: Int, // Table number
    modifier: Modifier = Modifier

) {
    val products by cartViewModel.cartProducts.collectAsState()
    val totalPrice = products.sumOf { it.pQuantity * it.pPrice }
    val serviceTax = totalPrice * 0.06
    val finalPrice = totalPrice + serviceTax

    val decimalFormat = DecimalFormat("#.##")
    val scope = rememberCoroutineScope()

    val customerId = "C1"
    val tableNumber = "T1"

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bill",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Customer ID: $customerId", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Table Number: $tableNumber", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        products.forEach { product ->
            Text(
                text = "${product.pName}: ${product.pQuantity} x ${decimalFormat.format(product.pPrice)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Total Price: RM ${decimalFormat.format(totalPrice)}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Service Tax (6%): RM ${decimalFormat.format(serviceTax)}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Final Price: RM ${decimalFormat.format(finalPrice)}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                // Insert each product as a separate order
                products.forEach { product ->
                    val order = com.example.order.database.Order(
                        pId = product.pId,
                        oPrice = product.pPrice,
                        oQuantity = product.pQuantity,
                        oTable = tableNumber,
                        customerId = customerId
                    )
                    orderViewModel.insertOrder(order)
                }
                // Clear the cart
                cartViewModel.clearCart()
                // Navigate back to menu
                navController.navigate(NavigationDestination.OrderMenu.route)
            }
        }) {
            Text(text = "Done")
        }
    }
}