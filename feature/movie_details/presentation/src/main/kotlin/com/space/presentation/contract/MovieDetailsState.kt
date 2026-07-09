package com.space.presentation.contract

import androidx.annotation.StringRes
import com.space.presentation.model.MovieDetailsUiModel

data class MovieDetailsState(
    val isLoading: Boolean = false,
    @StringRes
    val errorMessage: Int? = null,
    val movie: MovieDetailsUiModel? = null,
    val isFavourite: Boolean = false
)
