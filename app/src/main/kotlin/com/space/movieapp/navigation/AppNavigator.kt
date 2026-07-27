package com.space.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.space.api.navigation.FavouritesRoute
import com.space.api.navigation.MovieDetailsRoute
import com.space.api.navigation.MovieRoute

class AppNavigator(
    val backStack: NavBackStack<NavKey>
) {
    fun toDetails(id: Int) {
        backStack.add(MovieDetailsRoute(movieId = id))
    }

    fun back() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    fun toHome() {
        if (backStack.lastOrNull() != MovieRoute) {
            backStack.clear()
            backStack.add(MovieRoute)
        }
    }

    fun toFavourites() {
        if (backStack.lastOrNull() != FavouritesRoute) {
            backStack.clear()
            backStack.add(MovieRoute)
            backStack.add(FavouritesRoute)
        }
    }
}

@Composable
fun rememberAppNavigator(): AppNavigator {
    val backStack = rememberNavBackStack(MovieRoute)
    return remember(backStack) { AppNavigator(backStack) }
}