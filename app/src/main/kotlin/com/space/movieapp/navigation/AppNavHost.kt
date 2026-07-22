package com.space.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.space.api.navigation.MovieDetailsRoute
import com.space.api.navigation.MovieRoute
import com.space.api.navigation.movieDetailsEntries
import com.space.api.navigation.movieEntries
import com.space.navigation.FavouritesRoute

@Composable
fun AppNavHost() {
    val backStack = rememberNavBackStack(MovieRoute)

    val navigateToDetails: (Int) -> Unit = { id ->
        backStack.add(MovieDetailsRoute(movieId = id))
    }

    val navigateToFavourites: () -> Unit = {
        backStack.add(FavouritesRoute)
    }

    val navigateBack: () -> Unit = {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }
    NavDisplay(
        backStack = backStack,
        onBack = navigateBack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            movieEntries(
                onNavigateToDetails = navigateToDetails,
                onNavigateToFavourites = navigateToFavourites
            )
            movieDetailsEntries(
                onNavigateBack = navigateBack
            )
        }
    )
}