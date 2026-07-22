package com.space.presentation.contract

import com.space.ui.component.MovieCardUiModel

data class FavouritesState(
    val isLoading: Boolean = false,
    val favourites: List<MovieCardUiModel> = emptyList(),
    val error: String? = null
)
