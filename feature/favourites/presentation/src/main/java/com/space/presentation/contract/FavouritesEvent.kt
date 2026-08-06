package com.space.presentation.contract

import com.space.ui.component.MovieCardUiModel

sealed interface FavouritesEvent {
    data object LoadFavourites : FavouritesEvent
    data class OnMovieClicked(val movieId: Int) : FavouritesEvent
    data class OnFavouriteToggle(val movie: MovieCardUiModel) : FavouritesEvent
    data object OnRetryClicked : FavouritesEvent
}