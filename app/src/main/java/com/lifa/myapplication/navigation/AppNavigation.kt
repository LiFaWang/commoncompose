package com.lifa.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType // 导入 NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument // 导入 navArgument
import com.lifa.myapplication.ui.screens.LoginScreen
import com.lifa.myapplication.ui.screens.MainScreen
import com.lifa.myapplication.ui.screens.SettingsScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppDestinations.LOGIN_ROUTE) {
        composable(AppDestinations.LOGIN_ROUTE) {
            LoginScreen(navController = navController)
        }
        composable(AppDestinations.MAIN_ROUTE) {
            MainScreen(mainNavController = navController)
        }
        composable(
            route = AppDestinations.SETTINGS_ROUTE, // 使用带占位符的路由
            arguments = listOf( // 定义参数及其类型
                navArgument(AppDestinations.ARG_USER_ID) { type = NavType.StringType },
                navArgument(AppDestinations.ARG_SOURCE) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 从 backStackEntry 中提取参数
            val userId = backStackEntry.arguments?.getString(AppDestinations.ARG_USER_ID)
            val source = backStackEntry.arguments?.getString(AppDestinations.ARG_SOURCE)
            SettingsScreen(
                navController = navController,
                userId = userId,
                source = source
            )
        }
    }
}
