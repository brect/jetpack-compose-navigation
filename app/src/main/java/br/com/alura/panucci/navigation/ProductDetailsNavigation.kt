package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen


private const val productDetailsRoute = "productDetails"
private const val productIdArgument = "productId"

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
  composable(
    "${productDetailsRoute}/{$productIdArgument}"
  ) { backStackEntry ->

    val id = backStackEntry.arguments?.getString(productIdArgument)
    Log.i("MainActivity", "onCreate: productId - $id")

    sampleProducts.find {
      id == it.id
    }?.let { product ->
      ProductDetailsScreen(
        product = product,
        onNavigateToCheckout = {
          navController.navigateToCheckout()
        })
    } ?: LaunchedEffect(Unit) {
//        Toast.makeText(
//          this@MainActivity,
//          "Detalhes do Produto não encontrado",
//          Toast.LENGTH_SHORT
//        ).show()

      navController.popBackStack()
    }
  }
}

fun NavController.navigateToProductDetails(productId: String) {
  navigate("${productDetailsRoute}/$productId")
}