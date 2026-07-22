package com.space.domain.usecase

import com.space.domain.model.FavouriteMovieResponse
import com.space.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavouritesUseCase(private val repository: FavouriteRepository) {
    operator fun invoke(): Flow<List<FavouriteMovieResponse>> {
        return repository.getAllFavourites()
    }
}