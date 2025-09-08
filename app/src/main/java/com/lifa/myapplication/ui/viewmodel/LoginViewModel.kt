package com.lifa.myapplication.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifa.myapplication.data.AuthManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 可以定义一个密封类或枚举来表示更复杂的导航事件
sealed class LoginNavigationEvent {
    object NavigateToMain : LoginNavigationEvent()
    object Idle : LoginNavigationEvent() // 初始状态或无导航事件
}

class LoginViewModel(private val authManager: AuthManager) : ViewModel() { // 注入 AuthManager

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // 状态用于触发导航
    var navigationEvent by mutableStateOf<LoginNavigationEvent>(LoginNavigationEvent.Idle)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Email and password cannot be empty."
            return
        }

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            // 模拟网络请求
            delay(2000)

            // 模拟登录成功/失败逻辑
            if (email == "test@example.com" && password == "password") {
                // 登录成功，保存登录信息并更新导航事件
                authManager.saveLogin(token = "fake_token_${System.currentTimeMillis()}", email = email)
                navigationEvent = LoginNavigationEvent.NavigateToMain
            } else {
                errorMessage = "Invalid email or password."
            }
            isLoading = false
        }
    }

    // 调用此函数以重置导航事件，防止在配置更改（如屏幕旋转）后重新触发导航
    fun onNavigationHandled() {
        navigationEvent = LoginNavigationEvent.Idle
    }
}
