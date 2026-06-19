package com.space.common.network

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> apiCall(
    apiCall: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResult.Success(body)
            } else {
                ApiResult.Error(NetworkErrorMessages.NULL_BODY)
            }
        } else {
            ApiResult.Error(
                message = response.errorBody()?.toString()
                    ?: "${NetworkErrorMessages.HTTP} ${response.code()}: ${response.message()}"
            )
        }
    } catch (e: HttpException) {
        ApiResult.Error(
            message = "${NetworkErrorMessages.HTTP_ERROR}: ${e.code()}",
            throwable = e
        )
    } catch (e: IOException) {
        ApiResult.Error(
            message = NetworkErrorMessages.NETWORK_ERROR,
            throwable = e
        )
    } catch (e: Exception) {
        ApiResult.Error(
            message = e.localizedMessage ?: NetworkErrorMessages.UNEXPECTED_ERROR,
            throwable = e
        )
    }
}