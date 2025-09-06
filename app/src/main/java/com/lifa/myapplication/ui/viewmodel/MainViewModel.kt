package com.lifa.myapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifa.myapplication.data.model.Post
import com.lifa.myapplication.data.model.User
import com.lifa.myapplication.data.network.NetworkResult
import com.lifa.myapplication.data.repository.PostRepository
import com.lifa.myapplication.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn // 用于在特定作用域启动 Flow 收集
import kotlinx.coroutines.flow.onEach // 用于对每个发出的项执行操作
import kotlinx.coroutines.flow.onStart // 用于在 Flow 开始收集前执行操作
import kotlinx.coroutines.launch

class MainViewModel(private val postRepository: PostRepository) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState.asStateFlow()

    private val _uiUserState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiUserState: StateFlow<UiState<User>> = _uiUserState.asStateFlow()


    init {
        // ViewModel 初始化时自动获取数据
        fetchPosts()
        fetchUser()
    }



    fun fetchPosts() {
        postRepository.fetchPostsFlow()
            .onStart {
                _uiState.value = UiState.Loading

            }
            .onEach { result ->
                _uiState.value = when (result) {
                    is NetworkResult.Success -> {
                        if (result.data.isEmpty()) {
                            UiState.Empty
                        } else {
                            UiState.Success(result.data)
                        }
                    }
                    is NetworkResult.Error -> {
                        UiState.Error("API Error ${result.code}: ${result.message}")
                    }
                    is NetworkResult.Exception -> {
                        UiState.Error("Network Exception: ${result.exception.localizedMessage}", result.exception)
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    fun fetchUser() {
        postRepository.fetchUsersFlow()
            .onStart {
                _uiUserState.value = UiState.Loading
            }
            .onEach { result ->
                _uiUserState.value = when (result) {
                    is NetworkResult.Success -> {
                        UiState.Success(result.data)
                    }
                    is NetworkResult.Error -> {
                        UiState.Error("API Error ${result.code}: ${result.message}")
                    }
                    is NetworkResult.Exception -> {
                        UiState.Error("Network Exception: ${result.exception.localizedMessage}", result.exception)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}


