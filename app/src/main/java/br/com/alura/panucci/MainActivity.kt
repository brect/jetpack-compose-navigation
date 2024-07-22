package br.com.alura.panucci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.panucci.sampledata.bottomAppBarItems
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.screens.CheckoutScreen
import br.com.alura.panucci.ui.screens.DrinksListScreen
import br.com.alura.panucci.ui.screens.HighlightsListScreen
import br.com.alura.panucci.ui.screens.MenuListScreen
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.ui.theme.PanucciTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {

      val navController = rememberNavController()


      LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener({ controller, destination, arguments ->
          val routes = navController.backQueue.map {
            it.destination.route
          }
        })
      }

      val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()

      val currentDestination = currentBackStackEntryAsState?.destination

      PanucciTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {

          val selectedItem by remember(currentDestination) {
            val item = currentDestination?.let { destination ->
              bottomAppBarItems.find {
                it.route == destination.route
              }
            } ?: bottomAppBarItems.first()
            mutableStateOf(item)
          }

          PanucciApp(
            bottomAppBarItemSelected = selectedItem,
            onBottomAppBarItemSelectedChange = {
              val route = it.route

              navController.navigate(route) {
                launchSingleTop = true
                popUpTo(route = route)
              }
            },
            onFabClick = {

              navController.navigate("checkour")

            }) {
            NavHost(
              navController = navController,
              startDestination = "highlight"
            ) {
              composable("highlight") {
                HighlightsListScreen(
                  products = sampleProducts,
                  onNavigateToDetails = {
                    navController.navigate("productsDetails")
                  },
                  onNavigateToCheckout = {
                    navController.navigate("checkout")
                  }
                )
              }
              composable("menu") {
                MenuListScreen(
                  products = sampleProducts,
                  onNavigateToDetails = {
                    navController.navigate("productsDetails")
                  }
                )
              }
              composable("drinks") {
                DrinksListScreen(products = sampleProducts,
                  onNavigateToDetails = {
                    navController.navigate("productsDetails")
                  })
              }

              composable("productsDetails") {
                ProductDetailsScreen(product = sampleProducts.random(), onNavigateToCheckout = {
                  navController.navigate("checkout")
                })
              }

              composable("checkout") {
                CheckoutScreen(products = sampleProducts)
              }
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanucciApp(
  bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
  onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
  onFabClick: () -> Unit = {},
  content: @Composable () -> Unit
) {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(text = "Ristorante Panucci")
        },
      )
    },
    bottomBar = {
      PanucciBottomAppBar(
        item = bottomAppBarItemSelected,
        items = bottomAppBarItems,
        onItemChange = onBottomAppBarItemSelectedChange,
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = onFabClick
      ) {
        Icon(
          Icons.Filled.PointOfSale,
          contentDescription = null
        )
      }
    }
  ) {
    Box(
      modifier = Modifier.padding(it)
    ) {
      content()
    }
  }
}

@Preview
@Composable
private fun PanucciAppPreview() {
  PanucciTheme {
    Surface {
      PanucciApp {}
    }
  }
}