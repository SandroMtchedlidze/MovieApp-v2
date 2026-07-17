package com.space.api.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.presentation.screen.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int) : NavKey

fun EntryProviderScope<NavKey>.movieDetailsEntries(
    onNavigateBack: () -> Unit
) {
    entry<MovieDetailsRoute> { route ->
        MovieDetailsScreen(
            movieId = route.movieId,
            onNavigateBack = onNavigateBack
        )
    }
}