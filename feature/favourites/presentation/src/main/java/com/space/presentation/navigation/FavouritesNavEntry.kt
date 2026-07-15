package com.space.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.api.navigation.FavouritesRoute
import com.space.presentation.screen.FavouritesScreen


fun EntryProviderScope<NavKey>.favouritesEntry() {
    entry<FavouritesRoute> {
        FavouritesScreen()
    }
}