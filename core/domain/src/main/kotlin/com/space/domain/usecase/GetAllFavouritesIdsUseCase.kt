package com.space.domain.usecase

import com.space.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllFavouritesIdsUseCase(
    private val repository: FavouriteRepository,
) {
    operator fun invoke(): Flow<Set<Int>> =
        repository.getAllFavourites()
            .map { entities -> entities.map { it.movieId }.toSet() }
}