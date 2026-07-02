package com.space.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Int,
    @SerialName("poster_path")
    val posterPath: String?,
    val genres: List<GenreDto> = emptyList(),
    val title: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("vote_average")
    val rating: Double?,
    val runtime: Int?,
    val overview: String?
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)