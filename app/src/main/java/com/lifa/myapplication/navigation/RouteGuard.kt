package com.lifa.myapplication.navigation

import androidx.navigation.NavController
import com.lifa.myapplication.ui.viewmodel.AuthViewModel

object RouteGuard {
    fun install(
        navController: NavController,
        authViewModel: AuthViewModel,
        protectedRoutePatterns: Set<String>,
        redirectNavController: NavController? = null
    ) {
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            val routePattern = destination.route ?: return@addOnDestinationChangedListener
            val requiresAuth = protectedRoutePatterns.any { pattern ->
                routePattern == pattern || routePattern.startsWith(pattern)
            }
            if (requiresAuth && !authViewModel.isLoggedIn) {
                if (routePattern != AppDestinations.LOGIN_ROUTE) {
                    val targetController = redirectNavController ?: controller
                    targetController.navigate(AppDestinations.LOGIN_ROUTE) {
                        popUpTo(controller.graph.startDestinationId) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}


