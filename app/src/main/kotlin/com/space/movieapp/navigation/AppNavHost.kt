package com.space.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.space.api.navigation.favouritesEntry
import com.space.api.navigation.movieDetailsEntries
import com.space.api.navigation.movieEntries

@Composable
fun AppNavHost(
    navigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = navigator.backStack,
        onBack = navigator::back,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            movieEntries(onNavigateToDetails = navigator::toDetails)
            movieDetailsEntries(onNavigateBack = navigator::back)
            favouritesEntry(onNavigateToDetails = navigator::toDetails)
        }
    )
}