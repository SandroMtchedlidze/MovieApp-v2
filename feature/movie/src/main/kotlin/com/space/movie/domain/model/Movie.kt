package com.space.movie.domain.model

data class Movie(
    val id: Int,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val genre: List<String>
)