package com.space.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.space.api.navigation.FavouritesRoute
import com.space.api.navigation.HomeRoute
import com.space.api.navigation.MovieDetailsRoute

enum class AppTab { HOME, FAVOURITES }

class AppNavController(
    val backStack: NavBackStack<NavKey>,
    private val timeProvider: () -> Long = System::currentTimeMillis
) {
    private var lastNavigationTime = 0L

    val currentTab: AppTab
        get() = when (backStack.firstOrNull()) {
            is FavouritesRoute -> AppTab.FAVOURITES
            else -> AppTab.HOME
        }

    fun navigateToDetails(movieId: Int) {
        val now = timeProvider()
        if (now - lastNavigationTime > NAVIGATION_THROTTLE_MS) {
            lastNavigationTime = now
            backStack.add(MovieDetailsRoute(movieId = movieId))
        }
    }

    private fun switchTab(route: NavKey) {
        if (backStack.size == 1 && backStack.first() == route) return
        backStack.clear()
        backStack.add(route)
    }

    fun navigateBack() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    fun navigateToHome() = switchTab(HomeRoute)

    fun navigateToFavourites() = switchTab(FavouritesRoute)

    val showBottomBar: Boolean
        get() = backStack.lastOrNull() !is MovieDetailsRoute

    private companion object {
        const val NAVIGATION_THROTTLE_MS = 500L
    }
}

@Composable
fun rememberAppNavController(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(HomeRoute),
): AppNavController = remember(backStack) {
    AppNavController(
        backStack
    )
}