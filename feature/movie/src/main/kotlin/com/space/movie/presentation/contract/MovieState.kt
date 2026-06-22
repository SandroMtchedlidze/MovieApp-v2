package com.space.movie.presentation.contract

import com.space.ui.component.MovieCardUiModel

//ask tomorrow on pr

data class MovieState(
    val movies: List<MovieCardUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)