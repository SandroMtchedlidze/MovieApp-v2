package com.space.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.space.api.navigation.FavouritesRoute
import com.space.api.navigation.HomeRoute
import com.space.api.navigation.MovieDetailsRoute

class AppNavController(
    val backStack: NavBackStack<NavKey>,
    private val timeProvider: () -> Long = System::currentTimeMillis
) {
    private var lastNavigationTime = 0L

    fun navigateToDetails(movieId: Int) {
        val now = timeProvider()
        if (now - lastNavigationTime > NAVIGATION_THROTTLE_MS) {
            lastNavigationTime = now
            backStack.add(MovieDetailsRoute(movieId = movieId))
        }
    }

    fun navigateBack() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    fun navigateToHome() {
        if (backStack.lastOrNull() != HomeRoute) {
            backStack.clear()
            backStack.add(HomeRoute)
        }
    }

    fun navigateToFavourites() {
        if (backStack.lastOrNull() != FavouritesRoute) {
            backStack.clear()
            backStack.add(FavouritesRoute)
        }
    }

    val showBottomBar: Boolean
        get() = backStack.lastOrNull() !is MovieDetailsRoute

    private companion object {
        const val NAVIGATION_THROTTLE_MS = 500L
    }
}

@Composable
fun rememberAppNavController(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(HomeRoute)
): AppNavController = remember(backStack) { AppNavController(backStack) }