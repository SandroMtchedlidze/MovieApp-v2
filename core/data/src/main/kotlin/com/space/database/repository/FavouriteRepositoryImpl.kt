package com.space.database.repository

import com.space.database.datasource.contract.FavouritesLocalDataSource
import com.space.database.mapper.MovieMapper
import com.space.domain.model.FavouriteMovieResponse
import com.space.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteRepositoryImpl(
    private val favouritesLocalDataSource: FavouritesLocalDataSource,
    private val mapper: MovieMapper
) : FavouriteRepository {

    override suspend fun addFavourite(movie: FavouriteMovieResponse) {
        val entity = mapper.mapToEntity(movie)
        favouritesLocalDataSource.addFavourite(entity)
    }

    override suspend fun removeFavourite(movie: FavouriteMovieResponse) {
        val entity = mapper.mapToEntity(movie)
        favouritesLocalDataSource.removeFavourite(entity)
    }

    override fun getAllFavourites(): Flow<List<FavouriteMovieResponse>> {
        return favouritesLocalDataSource.getAllFavourites().map { entityList ->
            mapper.mapToDomainList(entityList)
        }
    }

    override fun isFavourite(movieId: Int): Flow<Boolean> {
        return favouritesLocalDataSource.isFavourite(movieId)
    }
}