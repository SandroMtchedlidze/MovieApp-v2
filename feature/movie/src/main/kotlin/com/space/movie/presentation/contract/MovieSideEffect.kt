package com.space.movie.presentation.contract

sealed class MovieSideEffect {
    data class NavigateToDetails(val movieId: Int) : MovieSideEffect()
}