package com.lifa.myapplication.data.network // 或者一个更通用的 core.network 包

import java.io.IOException

/**
 * 一个密封类，用于包装网络请求的结果。
 * @param T 成功时返回的数据类型。
 */
sealed class NetworkResult<out T : Any> {
    /**
     * 表示网络请求成功。
     * @param data 成功获取的数据。
     */
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()

    /**
     * 表示网络请求失败，但服务器有响应（例如 HTTP 4xx, 5xx 错误）。
     * @param code HTTP 状态码。
     * @param message 错误信息，可能来自服务器的错误体。
     */
    data class Error(val code: Int, val message: String?) : NetworkResult<Nothing>()

    /**
     * 表示网络请求本身发生异常（例如无网络连接、超时）。
     * @param exception 捕获到的异常。
     */
    data class Exception(val exception: Throwable) : NetworkResult<Nothing>()
}
