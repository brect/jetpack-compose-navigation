package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alura.panucci.ui.components.BottomAppBarItem

@Composable
fun PanucciNavHost(navController: NavHostController) {
  NavHost(
    navController = navController,
    startDestination = highlightsListRoute
  ) {
    highlightsListScreen(navController)
    menuScreen(navController)
    drinksListScreen(navController)
    productDetailsScreen(navController)
    checkoutScreen(navController)
  }
}