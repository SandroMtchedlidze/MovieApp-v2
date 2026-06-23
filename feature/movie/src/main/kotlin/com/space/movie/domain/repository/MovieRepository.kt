package com.space.movie.domain.repository

import com.space.common.network.ApiResult
import com.space.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTopRatedMovies(page: Int): Flow<ApiResult<List<Movie>>>
    suspend fun getGenres(): Flow<ApiResult<Map<Int, String>>>
}