package com.space.data.remote.repository

import com.space.data.remote.api.GenreApi
import com.space.data.remote.api.MovieApi
import com.space.data.remote.mapper.MovieMapper
import com.space.domain.model.MovieResponse
import com.space.domain.repository.MovieRepository
import com.space.networking.network.ApiResult
import com.space.networking.network.apiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val genreApi: GenreApi,
    private val movieMapper: MovieMapper
) : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): Flow<ApiResult<List<MovieResponse>>> {
        return apiCall { movieApi.getMovies(page) }.map { result ->
            when (result) {
                is ApiResult.Success -> ApiResult.Success(
                    result.data.results.map { movieMapper.mapToDomain(it, genreCache) }
                )

                is ApiResult.Error -> result
                is ApiResult.Loading -> result
            }
        }
    }

    private var genreCache: Map<Int, String> = emptyMap()

    override fun getGenres(): Flow<ApiResult<Map<Int, String>>> = flow {
        if (genreCache.isNotEmpty()) {
            emit(ApiResult.Loading(isLoading = false))
            emit(ApiResult.Success(genreCache))
            return@flow
        }
        apiCall { genreApi.getGenres() }.collect { result ->
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