package com.space.database.mapper

import com.space.database.entity.FavouriteMovieEntity
import com.space.domain.model.FavouriteMovieResponse

class MovieMapper {
    fun mapToDomain(favouriteMovieEntity: FavouriteMovieEntity): FavouriteMovieResponse {
        return FavouriteMovieResponse(
            movieId = favouriteMovieEntity.id,
            title = favouriteMovieEntity.title,
            posterUrl = favouriteMovieEntity.posterUrl,
            genre = favouriteMovieEntity.genre,
            releaseDate = favouriteMovieEntity.releaseDate
        )
    }

    fun mapToEntity(favouriteMovieResponse: FavouriteMovieResponse): FavouriteMovieEntity {
        return FavouriteMovieEntity(
            id = favouriteMovieResponse.movieId,
            title = favouriteMovieResponse.title,
            posterUrl = favouriteMovieResponse.posterUrl,
            genre = favouriteMovieResponse.genre,
            releaseDate = favouriteMovieResponse.releaseDate
        )
    }

    fun mapToDomainList(entityList: List<FavouriteMovieEntity>): List<FavouriteMovieResponse> {
        return entityList.map { mapToDomain(it) }
    }
}