package com.space.movie.navigaiton

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.movie.presentation.MovieScreen
import com.space.navigation.MovieRoute

fun EntryProviderScope<NavKey>.movieEntries(
    onNavigateToDetails: (movieId: Int) -> Unit,
    onNavigateToFavourites: () -> Unit
) {
    entry<MovieRoute> {
        MovieScreen(
            onNavigateToFavourites = onNavigateToFavourites,
            onNavigateToDetails = onNavigateToDetails
        )
    }
}