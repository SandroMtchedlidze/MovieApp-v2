package com.space.domain.model

data class FavouriteMovieResponse(
    val movieId: Int,
    val title: String,
    val posterUrl: String,
    val genre: String,
    val releaseDate: String
)