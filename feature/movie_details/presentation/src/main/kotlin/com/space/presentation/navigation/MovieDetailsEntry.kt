package com.space.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.api.navigation.MovieDetailsRoute
import com.space.presentation.screen.MovieDetailsScreen

fun EntryProviderScope<NavKey>.movieDetailsEntry() {
    entry<MovieDetailsRoute> { key ->
        MovieDetailsScreen(movieId = key.movieId)
    }
}