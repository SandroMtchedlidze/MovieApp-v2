package com.space.favourites.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.favourites.FavouritesScreen
import com.space.navigation.FavouritesRoute
fun EntryProviderScope<NavKey>.favouritesEntry(
    onNavigateToDetails: (movieId: Int) -> Unit,
    onBack:() -> Unit
) {
    entry<FavouritesRoute> {
        FavouritesScreen(
            onBack = onBack,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}