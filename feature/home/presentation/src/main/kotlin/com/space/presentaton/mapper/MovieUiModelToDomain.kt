package com.space.presentaton.mapper

import com.space.domain.model.FavouriteMovieResponse
import com.space.ui.component.MovieCardUiModel

class MovieUiModelToDomain {
    fun uiModelToDomain(uiModel: MovieCardUiModel): FavouriteMovieResponse {
        return FavouriteMovieResponse(
            movieId = uiModel.id,
            posterUrl = uiModel.posterUrl,
            releaseDate = uiModel.releaseDate,
            genre = uiModel.genre,
            title = uiModel.title
        )
    }
}