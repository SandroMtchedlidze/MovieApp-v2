package com.space.domain.usecase

import com.space.domain.model.FavouriteMovieResponse
import com.space.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.first

class ToggleFavouriteUseCase(private val repository: FavouriteRepository) {
    suspend operator fun invoke(movie: FavouriteMovieResponse) {
        if (repository.isFavourite(movie.movieId).first()) {
            repository.removeFavourite(movie)
        } else {
            repository.addFavourite(movie)
        }
    }
}