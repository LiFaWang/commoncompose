package com.lifa.myapplication.di


import com.lifa.myapplication.ui.viewmodel.MainViewModel
import com.lifa.myapplication.data.network.ApiService
import com.lifa.myapplication.data.repository.PostRepository
import com.lifa.myapplication.data.repository.PostRepositoryImpl
import com.lifa.myapplication.ui.viewmodel.LoginViewModel
import com.lifa.myapplication.ui.viewmodel.ProfileViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // 提供 OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 用于查看网络请求和响应日志
            })
            .build()
    }

    // 提供 Retrofit 实例
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // 替换为你的 API Base URL
            .client(get()) // Koin 会自动注入 OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 提供 ApiService
    single {
        get<Retrofit>().create(ApiService::class.java) // Koin 会自动注入 Retrofit
    }
    // 提供 Repository
    single<PostRepository> { PostRepositoryImpl(get()) }
    // 提供 MainViewModel
    viewModel { MainViewModel(get()) } // Koin 会自动注入 ApiServic
    viewModel { LoginViewModel(/* 如果有依赖项，在这里注入 */) } // 添加 LoginViewModel
    viewModel { ProfileViewModel() } // 添加 ProfileViewModel
}