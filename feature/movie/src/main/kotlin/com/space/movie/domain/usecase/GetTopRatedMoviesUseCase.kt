package com.space.movie.domain.usecase

import com.space.common.network.ApiResult
import com.space.movie.domain.model.Movie
import com.space.movie.domain.repository.MovieRepository

class GetTopRatedMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): ApiResult<List<Movie>> {
        return repository.getTopRatedMovies(page)
    }
}