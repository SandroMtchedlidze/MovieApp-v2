package com.space.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.space.api.navigation.FavouritesRoute
import com.space.api.navigation.HomeRoute
import com.space.api.navigation.MovieDetailsRoute

enum class AppTab { HOME, FAVOURITES }

//ori backstack
class AppNavController(
    val homeBackStack: NavBackStack<NavKey>,
    val favouritesBackStack: NavBackStack<NavKey>,
    private val timeProvider: () -> Long = System::currentTimeMillis
) {
    private var lastNavigationTime = 0L
    var currentTab by mutableStateOf(AppTab.HOME)

    val backStack: NavBackStack<NavKey>
        get() = if (currentTab == AppTab.HOME) homeBackStack else favouritesBackStack

    fun navigateToDetails(movieId: Int) {
        val now = timeProvider()
        if (now - lastNavigationTime > NAVIGATION_THROTTLE_MS) {
            lastNavigationTime = now
            backStack.add(MovieDetailsRoute(movieId = movieId))
        }
    }
    //yvela clicks qondes delay.

    fun navigateBack() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    fun navigateToHome() {
        currentTab = AppTab.HOME
    }

    fun navigateToFavourites() {
        currentTab = AppTab.FAVOURITES
    }

    val showBottomBar: Boolean
        get() = backStack.lastOrNull() !is MovieDetailsRoute

    private companion object {
        const val NAVIGATION_THROTTLE_MS = 500L
    }
}

@Composable
fun rememberAppNavController(
    homeBackStack: NavBackStack<NavKey> = rememberNavBackStack(HomeRoute),
    favouritesBackStack: NavBackStack<NavKey> = rememberNavBackStack(FavouritesRoute)
): AppNavController = remember(homeBackStack, favouritesBackStack) {
    AppNavController(
        homeBackStack,
        favouritesBackStack
    )
}