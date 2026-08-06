package com.space.presentation.mapper

import com.space.domain.model.FavouriteMovieResponse
import com.space.ui.component.MovieCardUiModel

class MovieUiModelToDomain {
    fun toUiModel(response: FavouriteMovieResponse): MovieCardUiModel {
        return MovieCardUiModel(
            id = response.movieId,
            title = response.title,
            releaseDate = response.releaseDate,
            genre = response.genre,
            posterUrl = response.posterUrl,
            isFavourite = true
        )
    }

    fun toDomain(uiModel: MovieCardUiModel): FavouriteMovieResponse {
        return FavouriteMovieResponse(
            movieId = uiModel.id,
            title = uiModel.title,
            posterUrl = uiModel.posterUrl,
            genre = uiModel.genre,
            releaseDate = uiModel.releaseDate
        )
    }
}