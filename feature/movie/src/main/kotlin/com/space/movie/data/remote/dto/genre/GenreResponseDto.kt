package com.space.movie.data.remote.dto.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponseDto(
    val genres: List<GenreDto>
)