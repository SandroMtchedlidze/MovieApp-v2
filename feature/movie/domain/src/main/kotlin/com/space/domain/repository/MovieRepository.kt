package com.space.domain.repository

import com.space.domain.model.MovieResponse
import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTopRatedMovies(page: Int): Flow<ApiResult<List<MovieResponse>>>
    fun getGenres(): Flow<ApiResult<Map<Int, String>>>
}