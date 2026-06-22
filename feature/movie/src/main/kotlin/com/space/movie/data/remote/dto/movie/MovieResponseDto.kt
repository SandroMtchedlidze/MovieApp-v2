package com.space.movie.data.remote.dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    val page : Int,
    val results : List<MovieDto>,
    @SerialName("total_pages")
    val totalPages : Int
)