package com.space.api.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.presentaton.screen.MovieScreen
import kotlinx.serialization.Serializable

@Serializable
data object MovieRoute : NavKey

fun EntryProviderScope<NavKey>.movieEntries(
    onNavigateToDetails: (movieId: Int) -> Unit,
) {
    entry<MovieRoute> {
        MovieScreen(
            onMovieClicked = onNavigateToDetails,
        )
    }
}