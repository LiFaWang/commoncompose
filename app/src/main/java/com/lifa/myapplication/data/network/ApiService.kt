package com.lifa.myapplication.data.network

import com.lifa.myapplication.data.model.Post
import com.lifa.myapplication.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("posts") // 假设API端点是 "posts"
    suspend fun getPosts(): Response<List<Post>>



    @GET("user")
    suspend fun getUser(): Response<User>
}