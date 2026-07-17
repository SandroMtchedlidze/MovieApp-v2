package com.space.domain.usecase

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import com.space.domain.repository.MovieRepository
import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<MovieResponse>> {
        return repository.getMovies()
    }
}