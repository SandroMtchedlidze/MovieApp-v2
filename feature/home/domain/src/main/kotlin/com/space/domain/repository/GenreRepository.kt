package com.space.domain.repository

import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres(): Flow<ApiResult<Map<Int, String>>>
}