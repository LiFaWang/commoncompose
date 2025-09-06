package com.lifa.myapplication.data.network


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * 封装网络API调用以返回 Flow<NetworkResult<T>>.
 *
 * @param T 成功时响应体的类型.
 * @param call 一个 suspend lambda，执行实际的 Retrofit API 调用并返回 Response<T>.
 * @return 一个 Flow，它会发出 NetworkResult 包装的 API 调用结果.
 */
fun <T : Any> makeApiCallFlow(call: suspend () -> Response<T>): Flow<NetworkResult<T>> = flow {
    // Log.d("makeApiCallFlow", "Executing API call on ${Thread.currentThread().name}")
    val response = call()
    if (response.isSuccessful) {
        response.body()?.let {
            // Log.d("makeApiCallFlow", "API call successful")
            emit(NetworkResult.Success(it))
        } ?: run {
            // Log.e("makeApiCallFlow", "API call successful but body is null. Code: ${response.code()}")
            emit(NetworkResult.Error(response.code(), "Response body is null"))
        }
    } else {
        val errorMsg = response.errorBody()?.string() ?: "Unknown error"
        // Log.e("makeApiCallFlow", "API call failed. Code: ${response.code()}, Message: $errorMsg")
        emit(NetworkResult.Error(response.code(), errorMsg))
    }
}.catch { e ->
    // Log.e("makeApiCallFlow", "Exception during API call or flow processing", e)
    emit(NetworkResult.Exception(e))
}.flowOn(Dispatchers.IO)

// 如果你仍在使用非 Flow 版本的 safeApiCall，也可以放在这里
suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                NetworkResult.Success(it)
            } ?: NetworkResult.Error(response.code(), "Response body is null")
        } else {
            val errorMsg = response.errorBody()?.string() ?: "Unknown error"
            NetworkResult.Error(response.code(), errorMsg)
        }
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}
