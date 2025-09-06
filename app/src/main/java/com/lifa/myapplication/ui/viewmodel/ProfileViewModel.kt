package com.lifa.myapplication.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 导航事件，可以包含要传递的参数
sealed class ProfileNavigationEvent {
    data class NavigateToSettings(val userId: String, val source: String) : ProfileNavigationEvent()
    object Idle : ProfileNavigationEvent()
}

class ProfileViewModel : ViewModel() {

    // 假设这是从某个地方获取的用户ID
    private val currentUserId = "user123"

    var navigationEvent by mutableStateOf<ProfileNavigationEvent>(ProfileNavigationEvent.Idle)
        private set

    fun onGoToSettingsClicked() {
        // 可以在这里执行一些逻辑，比如获取最新的用户信息或检查权限
        viewModelScope.launch {
            // 模拟一些准备工作
            delay(100) // 模拟异步操作

            // 准备导航事件，包含参数
            navigationEvent = ProfileNavigationEvent.NavigateToSettings(
                userId = currentUserId,
                source = "ProfileScreen" // 表明是从ProfileScreen发起的导航
            )
        }
    }

    fun onNavigationHandled() {
        navigationEvent = ProfileNavigationEvent.Idle
    }
}
