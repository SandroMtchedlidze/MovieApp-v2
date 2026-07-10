package com.space.api.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.presentaton.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

fun EntryProviderScope<NavKey>.homeEntries(
    onNavigateToDetails: (movieId: Int) -> Unit,
) {
    entry<HomeRoute> {
        HomeScreen(
            onMovieClicked = onNavigateToDetails,
        )
    }
}