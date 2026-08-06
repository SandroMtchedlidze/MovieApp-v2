package com.space.data.remote.repository

import com.space.data.remote.cache.GenreCache
import com.space.data.remote.datasource.contract.GenreRemoteDataSource
import com.space.domain.repository.GenreRepository
import com.space.networking.network.ApiResult
import com.space.networking.network.ResponseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GenreRepositoryImpl(
    private val genreRemoteDataSource: GenreRemoteDataSource,
    private val responseHandler: ResponseHandler,
    private var genreCache: GenreCache
) : GenreRepository {

    override fun getGenres(): Flow<ApiResult<Map<Int, String>>> = flow {
        if (!genreCache.isEmpty) {
            emit(ApiResult.Loading(isLoading = false))
            emit(ApiResult.Success(genreCache.get()))
            return@flow
        }
        responseHandler.apiCall { genreRemoteDataSource.getGenres() }.collect { result ->
            when (result) {
                is ApiResult.Loading -> emit(result)
                is ApiResult.Success -> {
                    val mapped = result.data.genres.associate { it.id to it.name }
                    genreCache.update(mapped)
                    emit(ApiResult.Success(mapped))
                }

                is ApiResult.Error -> emit(result)
            }
        }
    }
}