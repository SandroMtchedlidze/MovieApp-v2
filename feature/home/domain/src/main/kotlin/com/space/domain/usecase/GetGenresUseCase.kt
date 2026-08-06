package com.space.domain.usecase

import com.space.domain.repository.GenreRepository
import com.space.networking.network.ApiResult
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(
    private val repository: GenreRepository
) {
    operator fun invoke(): Flow<ApiResult<Map<Int, String>>> {
        return repository.getGenres()
    }
}