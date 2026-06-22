package com.space.movie.data.remote.repository

import com.space.common.network.ApiResult
import com.space.common.network.apiCall
import com.space.movie.data.mapper.toDomain
import com.space.movie.data.remote.api.GenreApi
import com.space.movie.data.remote.api.MovieApi
import com.space.movie.domain.model.Movie
import com.space.movie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val genreApi: GenreApi
) : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): ApiResult<List<Movie>> {
        return when (val result = apiCall { movieApi.getMovies(page) }) {
            is ApiResult.Success -> ApiResult.Success(
                result.data.results.map { it.toDomain(genreCache) }
            )

            is ApiResult.Error -> ApiResult.Error(
                message = result.message,
                throwable = result.throwable
            )

            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    private var genreCache: Map<Int, String> = emptyMap()

    override suspend fun getGenres(): ApiResult<Map<Int, String>> {
        if (genreCache.isEmpty()) return ApiResult.Success(genreCache)

        return when (val result = apiCall { genreApi.getGenres() }) {
            is ApiResult.Success -> {
                genreCache = result.data.genres.associate { it.id to it.name }
                ApiResult.Success(genreCache)
            }

            is ApiResult.Error -> ApiResult.Error(result.message, result.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }
}