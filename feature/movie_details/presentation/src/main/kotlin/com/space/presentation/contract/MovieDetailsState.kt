package com.space.presentation.contract

import com.space.presentation.model.MovieDetailsUiModel

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movie: MovieDetailsUiModel? = null,
    val isFavourite: Boolean = false
)
