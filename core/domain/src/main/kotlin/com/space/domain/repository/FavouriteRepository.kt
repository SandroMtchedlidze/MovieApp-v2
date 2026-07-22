package com.space.domain.repository

import com.space.domain.model.FavouriteMovieResponse
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    suspend fun addFavourite(movie: FavouriteMovieResponse)
    suspend fun removeFavourite(movie: FavouriteMovieResponse)
    fun getAllFavourites(): Flow<List<FavouriteMovieResponse>>
    fun isFavourite(movieId: Int): Flow<Boolean>
}