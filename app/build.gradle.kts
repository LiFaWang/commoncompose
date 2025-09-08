plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.lifa.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lifa.myapplication"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
    }

    buildTypes {
        debug {


        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    flavorDimensions += listOf("env")

    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "ENV_NAME", "\"dev\"")
            buildConfigField("String", "BASE_URL", "\"https://jsonplaceholder.typicode.com/\"")
            resValue("string", "app_name", "ComposeBase Dev")
        }
        create("qa") {
            dimension = "env"
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            buildConfigField("String", "ENV_NAME", "\"qa\"")
            buildConfigField("String", "BASE_URL", "\"https://jsonplaceholder.typicode.com/\"")
            resValue("string", "app_name", "ComposeBase QA")
        }
        create("prod") {
            dimension = "env"
            buildConfigField("String", "ENV_NAME", "\"prod\"")
            buildConfigField("String", "BASE_URL", "\"https://jsonplaceholder.typicode.com/\"")
            resValue("string", "app_name", "ComposeBase")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // 或者其他转换器，如 Moshi
    implementation(libs.logging.interceptor) // 用于日志记录 (可选)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose) // 如果你在Compose中使用Koin

    // Coroutines (Retrofit suspend functions need this)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.navigation.compose) // 请使用最新版本
    implementation(libs.androidx.compose.material.icons.core) // 为了底部导航图标
    implementation(libs.androidx.compose.material.icons.extended) // 为了更多图标选项

    // 通过OkHttp的拦截器机制
    // 实现在应用通知栏显示网络请求功能
    // https://github.com/ChuckerTeam/chucker
    // debug 下的依赖
    debugImplementation(libs.chucker)
    // prod 下的空依赖
    releaseImplementation(libs.chucker.no.op)

    // 启动页
    implementation(libs.androidx.core.splashscreen)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)
}