package com.space.domain.repository

import com.space.domain.model.MovieDetailsResponse
import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<ApiResult<MovieDetailsResponse>>
}