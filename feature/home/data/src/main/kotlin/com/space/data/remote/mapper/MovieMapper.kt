package com.space.data.remote.mapper

import com.space.data.remote.dto.movie.MovieDto
import com.space.domain.model.MovieResponse

fun MovieDto.toDomain(genreMap: Map<Int, String>): MovieResponse {
    return MovieResponse(
        id = id,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        genre = genreIds.mapNotNull { genreMap[it] }
    )
}