package com.space.movie.presentation.contract

sealed class MovieEvent {
    data object LoadMovies : MovieEvent()
    data class OnMovieClicked(val movieId: Int) : MovieEvent()
}