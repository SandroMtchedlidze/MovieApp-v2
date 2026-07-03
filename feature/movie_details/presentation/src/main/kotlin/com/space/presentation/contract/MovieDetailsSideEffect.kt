package com.space.presentation.contract

sealed interface MovieDetailsSideEffect {
    data object NavigateToBack : MovieDetailsSideEffect
}