package com.space.presentaton.contract

sealed class HomeEvent {
    data class OnMovieClicked(val movieId: Int) : HomeEvent()
}