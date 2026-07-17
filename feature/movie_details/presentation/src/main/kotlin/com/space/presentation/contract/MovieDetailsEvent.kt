package com.space.presentation.contract

sealed interface MovieDetailsEvent {
    data object OnBackClicked : MovieDetailsEvent
    data object OnFavouriteClicked : MovieDetailsEvent
    data object OnRetryClicked : MovieDetailsEvent
}