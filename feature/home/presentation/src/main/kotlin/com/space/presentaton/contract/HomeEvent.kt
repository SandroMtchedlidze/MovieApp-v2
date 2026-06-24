package com.space.presentaton.contract

sealed class HomeEvent {
    data object LoadMovies : HomeEvent()
    data class OnHomeClicked(val movieId: Int) : HomeEvent()
}