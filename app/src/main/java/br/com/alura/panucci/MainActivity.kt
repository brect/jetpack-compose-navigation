package br.com.alura.panucci

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.panucci.navigation.PanucciNavHost
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightsListRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.navigation.navigateSingleTopWithPopUpTo
import br.com.alura.panucci.navigation.navigateToCheckout
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.components.bottomAppBarItems
import br.com.alura.panucci.ui.theme.PanucciTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {

      val navController = rememberNavController()

      LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, _, _ ->
          val routes = navController.backQueue.map {
            it.destination.route
          }
          Log.i("MainActivity", "onCreate: back stack - $routes")
        }
      }

      val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()

      val currentDestination = currentBackStackEntryAsState?.destination

      PanucciTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {

          val selectedItem by remember(currentDestination) {
            val item = when (currentDestination?.route) {
              highlightsListRoute -> BottomAppBarItem.HighlightsList
              menuRoute -> BottomAppBarItem.Menu
              drinksRoute -> BottomAppBarItem.Drinks
              else -> BottomAppBarItem.HighlightsList
            }

            mutableStateOf(item)
          }

          val containsInBottomAppBarItems = when (currentDestination?.route) {
            highlightsListRoute, menuRoute, drinksRoute -> true
            else -> false
          }

          val isShowFab = when (currentDestination?.route) {
            menuRoute, drinksRoute -> true
            else -> false
          }

          PanucciApp(
            bottomAppBarItemSelected = selectedItem,
            onBottomAppBarItemSelectedChange = { item ->
              navController.navigateSingleTopWithPopUpTo(item)
            },
            onFabClick = {
              navController.navigateToCheckout()
            },
            isShowTopBar = containsInBottomAppBarItems,
            isShowBottomBar = containsInBottomAppBarItems,
            isShowFab = isShowFab
          ) {
            PanucciNavHost(navController)
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
  isShowTopBar: Boolean = false,
  isShowBottomBar: Boolean = false,
  isShowFab: Boolean = false,
  content: @Composable () -> Unit,
) {
  Scaffold(
    topBar = {
      if (isShowTopBar) {
        CenterAlignedTopAppBar(
          title = {
            Text(text = "Ristorante Panucci")
          },
        )
      }
    },
    bottomBar = {
      if (isShowBottomBar) {
        PanucciBottomAppBar(
          item = bottomAppBarItemSelected,
          items = bottomAppBarItems,
          onItemChange = onBottomAppBarItemSelectedChange,
        )
      }
    },
    floatingActionButton = {
      if (isShowFab) {
        FloatingActionButton(
          onClick = onFabClick
        ) {
          Icon(
            Icons.Filled.PointOfSale,
            contentDescription = null
          )
        }

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