package com.space.presentation.contract

sealed interface FavouritesEffect {
    data class NavigateToDetails(val movieId: Int) : FavouritesEffect
}