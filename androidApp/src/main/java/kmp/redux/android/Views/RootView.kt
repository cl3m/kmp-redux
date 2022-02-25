package kmp.redux.android.Views

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.Icon
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tab {
    Space, Counter
}

fun Tab.icon(): ImageVector {
    return when (this) {
        Tab.Space -> Icons.Filled.AutoAwesome
        Tab.Counter -> Icons.Filled.Tag
    }
}

@Composable
fun RootView(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Tab.values().forEach { tab ->
                    BottomNavigationItem(
                        icon = { Icon(tab.icon(), contentDescription = tab.name) },
                        label = { Text(text = tab.name) },
                        selected = currentRoute == tab.name,
                        onClick = {
                            navController.navigate(tab.name) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController, startDestination = Tab.Space.name) {
            composable(Tab.Space.name) {
                SpaceView()
            }
            composable(Tab.Counter.name) {
                CounterView()
            }
        }
    }
}