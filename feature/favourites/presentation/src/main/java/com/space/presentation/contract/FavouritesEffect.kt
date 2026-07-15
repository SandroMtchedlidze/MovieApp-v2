package com.space.presentation.contract

import com.space.navigation.NavigationCommand

sealed interface FavouritesEffect {
    data class Navigate(val command: NavigationCommand) : FavouritesEffect
}