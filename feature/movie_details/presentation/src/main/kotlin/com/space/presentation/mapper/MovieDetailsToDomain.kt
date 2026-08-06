package com.space.presentation.mapper

import com.space.domain.model.FavouriteMovieResponse
import com.space.presentation.model.MovieDetailsUiModel

class MovieDetailsToDomain {
    fun uiModelToDomain(uiModel: MovieDetailsUiModel): FavouriteMovieResponse {
        return FavouriteMovieResponse(
            movieId = uiModel.movieId,
            title = uiModel.title,
            releaseDate = uiModel.yearText,
            posterUrl = uiModel.posterUrl ?: "",
            genre = uiModel.genreText
        )
    }
}