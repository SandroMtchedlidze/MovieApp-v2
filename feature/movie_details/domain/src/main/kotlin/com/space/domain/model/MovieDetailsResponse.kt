package com.space.domain.model

data class MovieDetailsResponse(
    val id: Int,
    val posterPath: String?,
    val genre: String?,
    val title: String?,
    val releaseDate: String?,
    val rating: Double?,
    val runtime: Int?,
    val overview: String?
)