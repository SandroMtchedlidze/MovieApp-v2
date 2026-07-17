package com.space.networking.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ResponseHandlerImpl : ResponseHandler {
    override fun <T> apiCall(apiCall: suspend () -> Response<T>): Flow<ApiResult<T>> = flow {
        emit(ApiResult.Loading(isLoading = true))

        val result = runCatching { apiCall() }.fold(
            onSuccess = { response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        ApiResult.Success(body)
                    } else {
                        ApiResult.Error(NetworkError.EMPTY_RESPONSE)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorType = when (response.code()) {
                        401 -> NetworkError.UNAUTHORIZED
                        404 -> NetworkError.NOT_FOUND
                        else -> NetworkError.UNKNOWN
                    }
                    ApiResult.Error(errorType, message = errorBody)
                }
            },
            onFailure = { e ->
                val errorType = when (e) {
                    is IOException -> NetworkError.NO_INTERNET
                    is HttpException -> NetworkError.SERVER_UNREACHABLE
                    else -> NetworkError.UNKNOWN
                }
                ApiResult.Error(errorType, message = e.localizedMessage)
            }
        )
        emit(result)
        emit(ApiResult.Loading(isLoading = false))
    }

    override suspend fun <T> pagingApiCall(apiCall: suspend () -> Response<T>): PagingResult<T> {
        return runCatching { apiCall() }.fold(
            onSuccess = { response ->
                if (response.isSuccessful) {
                    response.body()?.let { PagingResult.Success(it) } ?: PagingResult.Error(
                        NetworkError.EMPTY_RESPONSE
                    )
                } else {
                    val errorType = when (response.code()) {
                        401 -> NetworkError.UNAUTHORIZED
                        404 -> NetworkError.NOT_FOUND
                        else -> NetworkError.UNKNOWN
                    }
                    PagingResult.Error(errorType, message = response.errorBody()?.toString())
                }
            },
            onFailure = { e ->
                val errorType = when (e) {
                    is IOException -> NetworkError.NO_INTERNET
                    is HttpException -> NetworkError.SERVER_UNREACHABLE
                    else -> NetworkError.UNKNOWN
                }
                PagingResult.Error(errorType, message = e.localizedMessage)
            }
        )
    }
}
