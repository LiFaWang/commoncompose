package com.lifa.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lifa.myapplication.navigation.AppDestinations // 导入 AppDestinations
import com.lifa.myapplication.ui.viewmodel.ProfileNavigationEvent
import com.lifa.myapplication.ui.viewmodel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController, // NavController 仍然由外部传入，用于执行实际导航
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val navigationEvent = viewModel.navigationEvent

    LaunchedEffect(navigationEvent) {
        when (val event = navigationEvent) { // 解构事件
            is ProfileNavigationEvent.NavigateToSettings -> {
                // 使用 AppDestinations 中的 helper function 来构建带参数的路由
                navController.navigate(
                    AppDestinations.settingsRouteWithArgs(event.userId, event.source)
                )
                viewModel.onNavigationHandled() // 重置事件
            }
            is ProfileNavigationEvent.Idle -> {
                // No-op
            }
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Profile Screen", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("User ID (from ViewModel): user123", style = MaterialTheme.typography.bodyMedium) // 示例
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                viewModel.onGoToSettingsClicked() // 调用 ViewModel 的方法
            }) {
                Text("Go to Settings with Params")
            }
        }
    }
}
