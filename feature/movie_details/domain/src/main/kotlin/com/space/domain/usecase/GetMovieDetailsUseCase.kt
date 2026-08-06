package com.space.domain.usecase

import com.space.domain.model.MovieDetailsResponse
import com.space.domain.repository.MovieDetailsRepository
import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(
    private val repository: MovieDetailsRepository
) {
    operator fun invoke(movieId: Int): Flow<ApiResult<MovieDetailsResponse>> {
        return repository.getMovieDetails(movieId)
    }
}