package com.space.movie.presentation.contract

import com.space.movie.domain.model.Movie

data class MovieState(
    val movies: List<Movie> = emptyList(),
    val isLoading : Boolean = false,
    val errorMessage: String? = null
)