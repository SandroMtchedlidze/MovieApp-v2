package com.space.data.remote.mapper

import com.space.data.remote.dto.MovieDetailsDto
import com.space.domain.model.MovieDetailsResponse

class MovieDetailsMapper {
    fun mapToDomain(dto: MovieDetailsDto): MovieDetailsResponse {
        return MovieDetailsResponse(
            id = dto.id,
            title = dto.title ?: "",
            posterPath = dto.posterPath ?: "",
            releaseDate = dto.releaseDate,
            genre = dto.genres.firstOrNull()?.name,
            rating = dto.rating,
            overview = dto.overview,
            runtime = dto.runtime
        )
    }
}