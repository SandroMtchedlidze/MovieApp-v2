package com.space.presentaton.contract

import com.space.ui.component.MovieCardUiModel

sealed class HomeEvent {
    data class OnMovieClicked(val movieId: Int) : HomeEvent()
    data class OnSearchQueryChanged(val query: String) : HomeEvent()
    data object OnSearchCleared : HomeEvent()
    data class OnGenreSelected(val genreId: Int) : HomeEvent()
    data object OnFilterClicked : HomeEvent()
    data class OnSearchFocusedChanged(val isFocused: Boolean) : HomeEvent()
    data class OnFavouriteClicked(val movie: MovieCardUiModel) : HomeEvent()

    data object OnRetryClicked : HomeEvent()
}