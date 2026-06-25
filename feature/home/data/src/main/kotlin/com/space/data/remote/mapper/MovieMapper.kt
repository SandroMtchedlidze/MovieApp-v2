package com.space.data.remote.mapper

import com.space.data.remote.dto.movie.MovieDto
import com.space.domain.model.MovieResponse

class MovieMapper {
    fun mapToDomain(dto: MovieDto, genreMap: Map<Int, String>): MovieResponse {
        return MovieResponse(
            id = dto.id,
            title = dto.title,
            posterPath = dto.posterPath ?: "",
            releaseDate = dto.releaseDate.substring(0, 4),
            genre = dto.genreIds.mapNotNull { genreMap[it] }
        )
    }
}