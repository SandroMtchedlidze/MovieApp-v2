package com.space.movieapp.navigation

import com.space.api.navigation.FavouritesRoute
import com.space.api.navigation.HomeRoute
import com.space.api.navigation.MovieDetailsRoute
import com.space.navigation.Navigator

enum class AppTab { HOME, FAVOURITES }

val Navigator.currentTab: AppTab
    get() = when (backStack.lastOrNull()) {
        is FavouritesRoute -> AppTab.FAVOURITES
        else -> AppTab.HOME
    }

val Navigator.showBottomBar: Boolean
    get() = backStack.lastOrNull() !is MovieDetailsRoute

fun Navigator.navigateToHome() = push(HomeRoute)

fun Navigator.navigateToFavourites() = push(FavouritesRoute)