package com.space.movie.data.remote.dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("release_date")
    val releaseDate: String,
    val title: String
)