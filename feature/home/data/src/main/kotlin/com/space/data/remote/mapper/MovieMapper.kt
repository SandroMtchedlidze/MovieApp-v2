package com.space.data.remote.mapper

import com.space.data.remote.dto.movie.MovieDto
import com.space.domain.model.MovieResponse

class MovieMapper {
    fun mapToDomain(dto: MovieDto): MovieResponse {
        return MovieResponse(
            id = dto.id,
            title = dto.title,
            posterPath = dto.posterPath ?: "",
            releaseDate = dto.releaseDate?.take(4),
            genre = dto.genreIds
        )
    }
}