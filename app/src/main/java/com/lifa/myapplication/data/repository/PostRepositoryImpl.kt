package com.lifa.myapplication.data.repository

import com.lifa.myapplication.data.model.Post
import com.lifa.myapplication.data.model.User
import com.lifa.myapplication.data.network.ApiService
import com.lifa.myapplication.data.network.NetworkResult
import com.lifa.myapplication.data.network.makeApiCallFlow // 导入辅助函数
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(private val apiService: ApiService) : PostRepository {
    override fun fetchPostsFlow(): Flow<NetworkResult<List<Post>>> {
        return makeApiCallFlow { apiService.getPosts() }
    }

    override fun fetchUsersFlow(): Flow<NetworkResult<User>> {
        return makeApiCallFlow { apiService.getUser() }
    }
}
