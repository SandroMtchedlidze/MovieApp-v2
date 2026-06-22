package com.space.movie.data.mapper

import com.space.movie.data.remote.dto.movie.MovieDto
import com.space.movie.domain.model.Movie

fun MovieDto.toDomain(genreMap: Map<Int, String>): Movie {
    return Movie(
        id = id,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        genre = genreIds.mapNotNull { genreMap[it] }
    )
}