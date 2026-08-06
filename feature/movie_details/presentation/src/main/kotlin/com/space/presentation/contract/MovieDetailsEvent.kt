package com.space.presentation.contract

import com.space.presentation.model.MovieDetailsUiModel

sealed interface MovieDetailsEvent {
    data object OnBackClicked : MovieDetailsEvent
    data class OnFavouriteClicked(val movieDetailsUiModel: MovieDetailsUiModel) : MovieDetailsEvent
    data object OnRetryClicked : MovieDetailsEvent
}