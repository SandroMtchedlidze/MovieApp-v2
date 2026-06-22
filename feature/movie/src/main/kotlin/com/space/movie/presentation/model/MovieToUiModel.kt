package com.space.movie.presentation.model

import com.space.feature.movie.R
import com.space.movie.domain.model.Movie
import com.space.ui.component.MovieCardUiModel

private val POSTER_BASE_URL = R.string.https_image_tmdb_org_t_p_w500

fun Movie.toUiModel(): MovieCardUiModel {
    return MovieCardUiModel(
        id = id,
        title = title,
        posterUrl = "$POSTER_BASE_URL$posterPath",
        genre = genre.firstOrNull() ?: "",
        releaseDate = releaseDate
    )
}