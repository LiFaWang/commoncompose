package com.lifa.myapplication.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lifa.myapplication.navigation.AppDestinations
import com.lifa.myapplication.navigation.bottomNavItems
import com.lifa.myapplication.navigation.RouteGuard
import org.koin.androidx.compose.koinViewModel
import com.lifa.myapplication.ui.viewmodel.AuthViewModel

@Composable
fun MainScreen(mainNavController: NavHostController) { // 接收来自 AppNavHost 的 NavController
    val bottomNavController = rememberNavController() // 用于底部导航内部的切换
    val authViewModel: AuthViewModel = koinViewModel()

    // 对底部导航的 NavController 同样安装集中守卫
    RouteGuard.install(
        navController = bottomNavController,
        authViewModel = authViewModel,
        protectedRoutePatterns = setOf(
            AppDestinations.SEARCH_SCREEN_ROUTE,
            AppDestinations.PROFILE_SCREEN_ROUTE,
            AppDestinations.MORE_OPTIONS_ROUTE
        ),
        redirectNavController = mainNavController
    )

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
                SearchScreen(navController = mainNavController)
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
    // 使用 Row 替代 NavigationBar
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // 设置合适的高度
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            // 创建一个空的 InteractionSource 来禁用水波纹效果
            val interactionSource = remember { MutableInteractionSource() }

            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null // 禁用指示器
                    ) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.label,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = screen.label,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}




