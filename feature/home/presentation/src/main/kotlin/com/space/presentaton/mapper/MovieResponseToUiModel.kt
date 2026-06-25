package com.space.presentaton.mapper

import com.space.domain.model.MovieResponse
import com.space.ui.component.MovieCardUiModel

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

class MovieResponseToUiModel {
    fun mapToUiModel(movie: MovieResponse): MovieCardUiModel {
        return MovieCardUiModel(
            id = movie.id,
            title = movie.title,
            posterUrl = "$POSTER_BASE_URL${movie.posterPath}",
            genre = movie.genre.firstOrNull() ?: "",
            releaseDate = movie.releaseDate
        )
    }
}