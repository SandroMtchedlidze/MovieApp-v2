package com.space.movie.data.remote.repository

import com.space.common.network.ApiResult
import com.space.common.network.apiCall
import com.space.movie.data.mapper.toDomain
import com.space.movie.data.remote.api.MovieApi
import com.space.movie.domain.model.Movie
import com.space.movie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: MovieApi
) : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): ApiResult<List<Movie>> {
        return when (val result = apiCall { api.getMovies(page) }) {
            is ApiResult.Success -> ApiResult.Success(
                result.data.results.map { it.toDomain() }
            )

            is ApiResult.Error -> ApiResult.Error(
                message = result.message,
                throwable = result.throwable
            )
            is ApiResult.Loading -> ApiResult.Loading
        }
    }
}