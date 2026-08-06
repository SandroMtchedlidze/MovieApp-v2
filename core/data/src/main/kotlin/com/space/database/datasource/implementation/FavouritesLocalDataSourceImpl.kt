package com.space.database.datasource.implementation

import com.space.database.dao.FavouriteMovieDao
import com.space.database.datasource.contract.FavouritesLocalDataSource
import com.space.database.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

class FavouritesLocalDataSourceImpl(
    private val favouriteMovieDao: FavouriteMovieDao
) : FavouritesLocalDataSource {
    override suspend fun addFavourite(movie: FavouriteMovieEntity) {
        favouriteMovieDao.insertFavourite(movie)
    }

    override suspend fun removeFavourite(movie: FavouriteMovieEntity) {
        favouriteMovieDao.deleteFavourite(movie)
    }

    override fun getAllFavourites(): Flow<List<FavouriteMovieEntity>> {
        return favouriteMovieDao.getAllFavourites()
    }

    override fun isFavourite(movieId: Int): Flow<Boolean> {
        return favouriteMovieDao.isFavourite(movieId)
    }
}