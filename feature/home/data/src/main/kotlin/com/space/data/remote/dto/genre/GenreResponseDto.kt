package com.space.data.remote.dto.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponseDto(
    val genres: List<GenreDto>
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)