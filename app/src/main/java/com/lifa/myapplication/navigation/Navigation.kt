package com.lifa.myapplication.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// 路由定义
object AppDestinations {
    const val LOGIN_ROUTE = "login"
    const val MAIN_ROUTE = "main"

    // 修改 SETTINGS_ROUTE 以包含参数占位符
    // 参数的名称将在 NavHost 中定义
    const val SETTINGS_ROUTE_BASE = "settings" // 基础路由
    const val ARG_USER_ID = "userId"
    const val ARG_SOURCE = "source"
    const val SETTINGS_ROUTE = "$SETTINGS_ROUTE_BASE/{$ARG_USER_ID}/{$ARG_SOURCE}" // 完整的带参数路由

    // Helper function to create the route with arguments
    fun settingsRouteWithArgs(userId: String, source: String): String {
        return "$SETTINGS_ROUTE_BASE/$userId/$source"
    }


    const val HOME_SCREEN_ROUTE = "home"
    const val SEARCH_SCREEN_ROUTE = "search"
    const val PROFILE_SCREEN_ROUTE = "profile"
    const val MORE_OPTIONS_ROUTE = "more_options"
}

// 底部导航项的数据类
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

// 底部导航项列表
val bottomNavItems = listOf(
    BottomNavItem(
        label = "Home",
        icon = Icons.Filled.Home,
        route = AppDestinations.HOME_SCREEN_ROUTE
    ),
    BottomNavItem(
        label = "Search",
        icon = Icons.Filled.Search,
        route = AppDestinations.SEARCH_SCREEN_ROUTE
    ),
    BottomNavItem(
        label = "Profile",
        icon = Icons.Filled.AccountCircle,
        route = AppDestinations.PROFILE_SCREEN_ROUTE
    ),
    BottomNavItem( // 第四个 Tab 示例
        label = "More",
        icon = Icons.Filled.Settings, // 你可以换成更合适的图标
        route = AppDestinations.MORE_OPTIONS_ROUTE
    )
)
