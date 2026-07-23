package com.space.movieapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.space.api.navigation.FavouritesRoute
import com.space.api.navigation.MovieDetailsRoute
import com.space.api.navigation.MovieRoute
import com.space.api.navigation.favouritesEntry
import com.space.api.navigation.movieDetailsEntries
import com.space.api.navigation.movieEntries
import com.space.ui.theme.MovieAppTheme.colors

@Composable
fun AppNavHost(
    isOnline: Boolean
) {
    val backStack = rememberNavBackStack(MovieRoute)

    val navigateToDetails: (Int) -> Unit = { id ->
        backStack.add(MovieDetailsRoute(movieId = id))
    }
    val navigateBack: () -> Unit = {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }
    val navigateHome: () -> Unit = {
        if (backStack.lastOrNull() != MovieRoute) {
            backStack.clear()
            backStack.add(MovieRoute)
        }
    }

    val navigateToFavourites: () -> Unit = {
        if (backStack.lastOrNull() != FavouritesRoute) {
            backStack.clear()
            backStack.add(MovieRoute)
            backStack.add(FavouritesRoute)
        }
    }
    val showBottomBar = backStack.lastOrNull() !is MovieDetailsRoute

    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            if (showBottomBar && isOnline) {
                BottomBar(
                    backStack = backStack,
                    onHomeClick = navigateHome,
                    onFavouritesClick = navigateToFavourites
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = navigateBack,
            modifier = Modifier.padding(innerPadding),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                movieEntries(
                    onNavigateToDetails = navigateToDetails,
                )
                movieDetailsEntries(
                    onNavigateBack = navigateBack
                )
                favouritesEntry(onNavigateToDetails = navigateToDetails)
            }
        )
    }
}