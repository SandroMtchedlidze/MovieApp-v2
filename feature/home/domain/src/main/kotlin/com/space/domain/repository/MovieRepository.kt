package com.space.domain.repository

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<MovieResponse>>
    fun getGenres(): Flow<ApiResult<Map<Int, String>>>
}