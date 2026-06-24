package com.space.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.space.api.navigation.MovieRoute
import com.space.api.navigation.movieEntries
import com.space.favourites.navigation.favouritesEntry
import com.space.navigation.FavouritesRoute

@Composable
fun AppNavHost() {
    val backStack = rememberNavBackStack(MovieRoute)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            movieEntries({}, {
                backStack.add(FavouritesRoute)
            })
        }
    )
}