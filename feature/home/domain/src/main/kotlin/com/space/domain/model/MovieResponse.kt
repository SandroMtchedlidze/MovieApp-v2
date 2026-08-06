package com.space.domain.model

data class MovieResponse(
    val id: Int,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val genre: List<Int>
)