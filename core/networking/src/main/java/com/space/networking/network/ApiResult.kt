package com.space.networking.network

sealed class ApiResult<out T> {
    data class Loading(val isLoading: Boolean) : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(
        val networkError: NetworkError,
        val message: String? = null
    ) : ApiResult<Nothing>()
}