package com.space.movie.domain.usecase

import com.space.common.network.ApiResult
import com.space.movie.domain.repository.MovieRepository

class GetGenresUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): ApiResult<Map<Int, String>> {
        return repository.getGenres()
    }
}