package com.space.presentaton.mapper

import com.space.domain.model.MovieResponse
import com.space.ui.component.MovieCardUiModel

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

class MovieResponseToUiModel {
    fun mapToUiModel(movie: MovieResponse, genres: Map<Int, String>): MovieCardUiModel {
        val genreName = movie.genre.firstNotNullOfOrNull { id -> genres[id] } ?: ""
        return MovieCardUiModel(
            id = movie.id,
            title = movie.title ?: "",
            posterUrl = "$POSTER_BASE_URL${movie.posterPath}",
            genre = genreName,
            releaseDate = movie.releaseDate ?: ""
        )
    }
}