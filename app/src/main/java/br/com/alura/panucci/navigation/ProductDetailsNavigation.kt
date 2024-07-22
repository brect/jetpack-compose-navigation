package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
  composable(
    "${AppDestination.ProductDetails.route}/{productId}"
  ) { backStackEntry ->

    val id = backStackEntry.arguments?.getString("productId")
    Log.i("MainActivity", "onCreate: productId - $id")

    sampleProducts.find {
      id == it.id
    }?.let { product ->
      ProductDetailsScreen(
        product = product,
        onNavigateToCheckout = {
          navController.navigate(AppDestination.Checkout.route)
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
