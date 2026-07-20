package com.space.database.repository

import com.space.database.dao.FavouriteMovieDao
import com.space.database.mapper.MovieEntityMapper
import com.space.domain.model.FavouriteMovieResponse
import com.space.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteRepositoryImpl(
    private val dao: FavouriteMovieDao,
    private val mapper: MovieEntityMapper
) : FavouriteRepository {

    override suspend fun addFavourite(movie: FavouriteMovieResponse) {
        val entity = mapper.mapToEntity(movie)
        dao.insertFavourite(entity)
    }

    override suspend fun removeFavourite(movie: FavouriteMovieResponse) {
        val entity = mapper.mapToEntity(movie)
        dao.deleteFavourite(entity)
    }

    override fun getAllFavourites(): Flow<List<FavouriteMovieResponse>> {
        return dao.getAllFavourites().map { entityList ->
            mapper.mapToDomainList(entityList)
        }
    }

    override fun isFavourite(movieId: Int): Flow<Boolean> {
        return dao.isFavourite(movieId)
    }
}