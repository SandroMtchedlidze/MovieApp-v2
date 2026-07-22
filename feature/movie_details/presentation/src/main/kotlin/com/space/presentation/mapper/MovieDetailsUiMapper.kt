package com.space.presentation.mapper

import com.space.domain.model.MovieDetailsResponse
import com.space.presentation.model.MovieDetailsUiModel

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

class MovieDetailsUiMapper {
    fun mapToUi(domain: MovieDetailsResponse): MovieDetailsUiModel {
        return MovieDetailsUiModel(
            movieId = domain.id,
            posterUrl = domain.posterPath?.let { "$POSTER_BASE_URL$it" },
            title = domain.title ?: "Unknown title",
            ratingText = domain.rating?.let { "%.1f".format(it) } ?: "-",
            genreText = domain.genre ?: "-",
            overviewText = domain.overview ?: "No description",
            yearText = domain.releaseDate?.take(4) ?: "-",
            runtimeText = formatRuntime(domain.runtime)
        )
    }
}

private fun formatRuntime(minutes: Int?): String {
    if (minutes == null || minutes <= 0) return "-"
    val hours = minutes / 60
    val mins = minutes % 60
    return when {
        hours > 0 && mins > 0 -> "${hours}h ${mins}m"
        hours > 0 -> "${hours}h"
        else -> "${mins}m"
    }
}