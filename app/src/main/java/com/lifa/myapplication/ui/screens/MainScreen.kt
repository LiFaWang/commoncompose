package com.lifa.myapplication.ui.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lifa.myapplication.navigation.AppDestinations
import com.lifa.myapplication.navigation.bottomNavItems

@Composable
fun MainScreen(mainNavController: NavHostController) { // 接收来自 AppNavHost 的 NavController
    val bottomNavController = rememberNavController() // 用于底部导航内部的切换

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = AppDestinations.HOME_SCREEN_ROUTE, // 默认显示的 tab
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestinations.HOME_SCREEN_ROUTE) {
                HomeScreen() // PostScreen现在是HomeScreen
            }
            composable(AppDestinations.SEARCH_SCREEN_ROUTE) {
                SearchScreen()
            }
            composable(AppDestinations.PROFILE_SCREEN_ROUTE) {
                // ProfileScreen 现在需要 mainNavController 来跳转到 Settings
                ProfileScreen(navController = mainNavController)
            }
            composable(AppDestinations.MORE_OPTIONS_ROUTE) {
                MoreOptionsScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
