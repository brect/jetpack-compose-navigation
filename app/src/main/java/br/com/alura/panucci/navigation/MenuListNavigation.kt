package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.MenuListScreen

private const val menuNavigationRoute = "menu"

fun NavGraphBuilder.menuScreen(navController: NavHostController) {
  composable(menuNavigationRoute) {
    MenuListScreen(
      products = sampleProducts,
      onNavigateToDetails = { product ->
        navController.navigateToProductDetails(product.id)
      }
    )
  }
}

fun NavController.navigateToMenu() {
  navigate(menuNavigationRoute)
}
