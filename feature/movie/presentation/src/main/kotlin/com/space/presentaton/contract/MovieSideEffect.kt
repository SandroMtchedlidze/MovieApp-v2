package com.space.presentaton.contract

sealed class MovieSideEffect {
    data class NavigateToDetails(val movieId: Int) : MovieSideEffect()
}