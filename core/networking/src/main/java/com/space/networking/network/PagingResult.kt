package com.space.networking.network

sealed class PagingResult<out T> {
    data class Success<T>(val data: T) : PagingResult<T>()
    data class Error(val error: NetworkError, val message: String? = null) : PagingResult<Nothing>()
}