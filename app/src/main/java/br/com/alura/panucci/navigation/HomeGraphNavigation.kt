package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import br.com.alura.panucci.ui.components.BottomAppBarItem

internal const val homeGraphRoute = "home"

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
  navigation(
    startDestination = highlightsListRoute,
    route = homeGraphRoute
  ) {
    highlightsListScreen(navController)
    menuScreen(navController)
    drinksListScreen(navController)
  }
}

fun NavController.navigateToHomeGraph(){
  navigate(homeGraphRoute)
}

fun NavController.navigateSingleTopWithPopUpTo(
  item: BottomAppBarItem
) {
  val (route, navigate) = when (item) {
    BottomAppBarItem.HighlightsList -> Pair(
      highlightsListRoute,
      ::navigateToHighlightsList
    )

    BottomAppBarItem.Menu -> Pair(
      menuRoute,
      ::navigateToMenu
    )

    BottomAppBarItem.Drinks -> Pair(
      drinksRoute,
      ::navigateToDrinks
    )
  }

  val navOptions = navOptions {
    launchSingleTop = true
    popUpTo(route)
  }

  navigate(navOptions)
}