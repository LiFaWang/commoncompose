package com.lifa.myapplication


import android.app.Application
import com.lifa.myapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG) // Koin 日志，可选
            androidContext(this@MyApplication)
            modules(appModule) // 加载你的 Koin 模块
        }
    }
}
