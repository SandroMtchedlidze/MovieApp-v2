package com.space.presentation.contract

import com.space.navigation.NavigationCommand

sealed interface MovieDetailsSideEffect {
    data class Navigate(val command: NavigationCommand) : MovieDetailsSideEffect
}