package com.space.domain.usecase


import com.space.domain.model.MovieResponse
import com.space.domain.repository.MovieRepository
import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

class GetTopRatedMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): Flow<ApiResult<List<MovieResponse>>> {
        return repository.getTopRatedMovies(page)
    }
}