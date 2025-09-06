package com.lifa.myapplication.data.repository


import com.lifa.myapplication.data.model.Post
import com.lifa.myapplication.data.model.User
import com.lifa.myapplication.data.network.NetworkResult // 导入封装类
import kotlinx.coroutines.flow.Flow


interface PostRepository {
    // 方法现在返回 Flow
    fun fetchPostsFlow(): Flow<NetworkResult<List<Post>>>


    fun fetchUsersFlow(): Flow<NetworkResult<User>>
}







