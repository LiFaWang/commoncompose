package com.lifa.myapplication.ui.screens

import com.lifa.myapplication.ui.viewmodel.MainViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lifa.myapplication.data.model.Post
import com.lifa.myapplication.ui.common.UiState
import com.lifa.myapplication.ui.theme.MyApplicationTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    // 使用 collectAsState 将 StateFlow 转换为 Compose State
    // 当 uiState 的值改变时，HomeScreen 会自动重组
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.fetchPosts() },
            // 禁用按钮的条件可以根据 uiState 来判断
            enabled = uiState !is UiState.Loading
        ) {
            Text(if (uiState is UiState.Loading) "Fetching..." else "Refresh Posts")
        }

        // 根据 uiState 的不同状态显示不同的 UI
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            is UiState.Success<List<Post>> -> { // 类型检查现在更具体

                LazyColumn() {
                    items(state.data) { post ->
                        PostItem(post = post)
                    }
                }
            }
            is UiState.Empty -> {
                Text("No posts found.", modifier = Modifier.padding(16.dp))
            }
            is UiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
                // 你甚至可以根据 state.throwable 做一些特定的 UI 展示或操作
            }
            // 如果 UiState 是密封接口，且 when 表达式用作语句，则不需要 else。
            // 如果用作表达式并赋值给变量，则可能需要 else 或确保所有情况都被覆盖。
        }

    }}
        @Composable
fun PostItem(post: Post, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Text(text = post.body, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// Preview 可能需要调整，因为它不再直接接收 Scaffold 的 innerPadding
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        // val fakeViewModel = ... (你的 fake ViewModel 实现)
        // HomeScreen(viewModel = fakeViewModel)
        Text("Preview for HomeScreen (PostScreen)") // 简化预览
    }
}
