package com.space.movie.data.mapper

import com.space.movie.data.remote.dto.MovieDto
import com.space.movie.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title
    )
}