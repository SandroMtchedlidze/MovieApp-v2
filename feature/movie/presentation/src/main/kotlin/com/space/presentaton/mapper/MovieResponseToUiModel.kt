package com.space.presentaton.mapper

import com.space.domain.model.MovieResponse
import com.space.ui.component.MovieCardUiModel

private val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieResponse.toUiModel(): MovieCardUiModel {
    return MovieCardUiModel(
        id = id,
        title = title,
        posterUrl = "$POSTER_BASE_URL$posterPath",
        genre = genre.firstOrNull() ?: "",
        releaseDate = releaseDate
    )
}