package com.space.presentaton.contract

sealed class HomeEvent {
    data class OnMovieClicked(val movieId: Int) : HomeEvent()
    data class OnSearchQueryChanged(val query: String) : HomeEvent()
    data object OnSearchCleared : HomeEvent()
}