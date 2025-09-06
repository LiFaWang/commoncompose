package com.lifa.myapplication.ui.common // 或者 com.yourpackage.core.ui

/**
 * 一个通用的密封接口，用于表示 UI 状态。
 * @param T 成功时的数据类型。
 */
sealed interface UiState<out T> { // 使用 'out T' 使其协变，更灵活
    /**
     * 表示数据正在加载中。
     */
    object Loading : UiState<Nothing> // 对于 Loading，数据类型 T 不重要

    /**
     * 表示操作成功，并包含数据。
     * @param data 成功获取的数据。
     */
    data class Success<out T>(val data: T) : UiState<T>

    /**
     * 表示操作失败，并包含错误信息。
     * @param message 错误信息。
     * @param throwable (可选) 相关的异常，用于调试或特定错误处理。
     */
    data class Error(val message: String, val throwable: Throwable? = null) : UiState<Nothing>

    /**
     * 表示成功获取数据，但数据为空。
     * 这比直接在 Success 中检查列表是否为空更明确。
     */
    object Empty : UiState<Nothing> // 对于 Empty，数据类型 T 不重要

    // 你还可以根据需要添加其他通用状态，例如：
    // object Uninitialized : UiState<Nothing> // 初始未加载状态
    // data class Refreshing<out T>(val previousData: T?) : UiState<T> // 正在刷新，但可能仍有旧数据
}


