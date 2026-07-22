package com.space.api.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.presentation.screen.FavouritesScreen
import kotlinx.serialization.Serializable

@Serializable
data object FavouritesRoute : NavKey

fun EntryProviderScope<NavKey>.favouritesEntry(
    onNavigateToDetails: (movieId: Int) -> Unit,
) {
    entry<FavouritesRoute> {
        FavouritesScreen(
            onNavigateToDetails = onNavigateToDetails
        )
    }
}