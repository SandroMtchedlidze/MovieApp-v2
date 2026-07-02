package com.space.networking.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ResponseHandler {
    fun <T> apiCall(apiCall: suspend () -> Response<T>): Flow<ApiResult<T>>
}