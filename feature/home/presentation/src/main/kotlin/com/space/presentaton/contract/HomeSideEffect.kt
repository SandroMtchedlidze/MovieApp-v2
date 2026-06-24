package com.space.presentaton.contract

sealed class HomeSideEffect {
    data class NavigateToDetails(val movieId: Int) : HomeSideEffect()
}