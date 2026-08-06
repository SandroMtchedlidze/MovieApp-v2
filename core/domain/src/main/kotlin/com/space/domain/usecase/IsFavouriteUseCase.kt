package com.space.domain.usecase

import com.space.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow

class IsFavouriteUseCase(private val repository: FavouriteRepository) {
    operator fun invoke(movieId: Int): Flow<Boolean> {
        return repository.isFavourite(movieId)
    }
}