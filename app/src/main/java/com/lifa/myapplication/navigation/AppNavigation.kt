package com.lifa.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavType // 导入 NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument // 导入 navArgument
import com.lifa.myapplication.ui.screens.LoginScreen
import com.lifa.myapplication.ui.screens.MainScreen
import com.lifa.myapplication.ui.screens.SettingsScreen
import org.koin.androidx.compose.koinViewModel
import com.lifa.myapplication.ui.viewmodel.AuthViewModel


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()

    // 集中式路由守卫：配置需要登录的路由（支持带参数的基路由前缀）
    val protectedRoutes = setOf(
        AppDestinations.SEARCH_SCREEN_ROUTE,
        AppDestinations.SETTINGS_ROUTE_BASE, // 使用 base，下面匹配 startsWith
        AppDestinations.PROFILE_SCREEN_ROUTE,
        AppDestinations.MORE_OPTIONS_ROUTE
    )

    RouteGuard.install(
        navController = navController,
        authViewModel = authViewModel,
        protectedRoutePatterns = protectedRoutes
    )
    NavHost(navController = navController, startDestination = AppDestinations.MAIN_ROUTE) {
        composable(AppDestinations.LOGIN_ROUTE) {
            LoginScreen(navController = navController)
        }
        composable(AppDestinations.MAIN_ROUTE) {
            MainScreen(mainNavController = navController)
        }

        composable(AppDestinations.SPLASH_ROUTE){
            SplashScreen
        }
        composable(
            route = AppDestinations.SETTINGS_ROUTE, // 使用带占位符的路由
            arguments = listOf( // 定义参数及其类型
                navArgument(AppDestinations.ARG_USER_ID) { type = NavType.StringType },
                navArgument(AppDestinations.ARG_SOURCE) { type = NavType.StringType }
            )
        ) { backStackEntry ->
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
