package com.space.networking.network


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> apiCall(
    apiCall: suspend () -> Response<T>
): Flow<ApiResult<T>> = flow {
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