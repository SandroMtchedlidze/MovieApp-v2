package com.space.presentaton.contract

import com.space.ui.component.MovieCardUiModel

data class MovieState(
    val movies: List<MovieCardUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)