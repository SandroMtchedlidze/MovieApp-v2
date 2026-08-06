package com.space.database.datasource.contract

import com.space.database.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavouritesLocalDataSource {
    suspend fun addFavourite(movie: FavouriteMovieEntity)
    suspend fun removeFavourite(movie: FavouriteMovieEntity)
    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>>
    fun isFavourite(movieId: Int): Flow<Boolean>
}