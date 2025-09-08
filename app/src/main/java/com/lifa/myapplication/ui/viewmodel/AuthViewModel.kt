package com.lifa.myapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifa.myapplication.data.AuthManager
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AuthViewModel(private val authManager: AuthManager) : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            authManager.authTokenFlow
                .map { !it.isNullOrEmpty() }
                .collect { loggedIn ->
                    isLoggedIn = loggedIn
                }
        }
    }
}



