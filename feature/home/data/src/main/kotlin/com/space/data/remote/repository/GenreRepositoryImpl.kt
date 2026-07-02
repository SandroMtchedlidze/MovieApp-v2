package com.space.data.remote.repository

import com.space.data.remote.api.GenreApi
import com.space.domain.repository.GenreRepository
import com.space.networking.network.ApiResult
import com.space.networking.network.ResponseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

var genreCache: Map<Int, String> = emptyMap()

class GenreRepositoryImpl(
    private val genreApi: GenreApi,
    private val responseHandler: ResponseHandler
) : GenreRepository {

    override fun getGenres(): Flow<ApiResult<Map<Int, String>>> = flow {
        if (genreCache.isNotEmpty()) {
            emit(ApiResult.Loading(isLoading = false))
            emit(ApiResult.Success(genreCache))
            return@flow
        }
        responseHandler.apiCall { genreApi.getGenres() }.collect { result ->
            when (result) {
                is ApiResult.Loading -> emit(result)
                is ApiResult.Success -> {
                    genreCache = result.data.genres.associate { it.id to it.name }
                    emit(ApiResult.Success(genreCache))
                }
                is ApiResult.Error -> emit(result)
            }
        }
    }
}