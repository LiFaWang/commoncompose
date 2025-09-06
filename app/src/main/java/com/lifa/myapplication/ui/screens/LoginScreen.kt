package com.lifa.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lifa.myapplication.navigation.AppDestinations
import com.lifa.myapplication.ui.viewmodel.LoginNavigationEvent
import com.lifa.myapplication.ui.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel // 确保导入

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel() // 使用 Koin 注入 ViewModel
) {
    val email = viewModel.email
    val password = viewModel.password
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val navigationEvent = viewModel.navigationEvent

    // 使用 LaunchedEffect 来观察导航事件
    // 当 navigationEvent 改变时，这个 effect 会被重新启动
    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is LoginNavigationEvent.NavigateToMain -> {
                navController.navigate(AppDestinations.MAIN_ROUTE) {
                    popUpTo(AppDestinations.LOGIN_ROUTE) {
                        inclusive = true
                    }
                }
                viewModel.onNavigationHandled() // 重置事件，防止重复导航
            }
            is LoginNavigationEvent.Idle -> {
                // 无操作
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login") })
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login to Your Account", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = errorMessage != null // 可以根据错误信息更细致地控制
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                isError = errorMessage != null
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login() },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Login")
                }
            }
        }
    }
}
