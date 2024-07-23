package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.DrinksListScreen

private const val drinksRoute = "drinks"

fun NavGraphBuilder.drinksListScreen(navController: NavHostController) {
  composable(AppDestination.Drinks.route) {
    DrinksListScreen(products = sampleProducts,
      onNavigateToDetails = { product ->
        navController.navigateToProductDetails(product.id)
      })
  }
}

fun NavController.navigateToDrinks() {
  navigate(drinksRoute)
}