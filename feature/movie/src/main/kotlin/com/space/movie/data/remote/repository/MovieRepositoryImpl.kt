package com.space.movie.data.remote.repository

import com.space.common.network.ApiResult
import com.space.common.network.apiCall
import com.space.movie.data.mapper.toDomain
import com.space.movie.data.remote.api.GenreApi
import com.space.movie.data.remote.api.MovieApi
import com.space.movie.domain.model.Movie
import com.space.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val genreApi: GenreApi
) : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): Flow<ApiResult<List<Movie>>> {
        return apiCall { movieApi.getMovies(page) }.map { result ->
            when (result) {
                is ApiResult.Success -> ApiResult.Success(
                    result.data.results.map { it.toDomain(genreCache) }
                )

                is ApiResult.Error -> result
                is ApiResult.Loading -> result
            }
        }
    }

    private var genreCache: Map<Int, String> = emptyMap()

    override suspend fun getGenres(): Flow<ApiResult<Map<Int, String>>> = flow {
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