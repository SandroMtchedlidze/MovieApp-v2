package com.space.networking.network

class PagingException(
    val errorType: NetworkError,
    message: String? = null
) : Exception(message)