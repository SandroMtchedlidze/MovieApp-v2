package com.space.movieapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.space.api.navigation.favouritesEntry
import com.space.api.navigation.movieDetailsEntries
import com.space.api.navigation.movieEntries
import com.space.ui.theme.MovieAppTheme.colors

@Composable
fun AppNavHost() {
    val navController = rememberAppNavController()
    val backStack = navController.backStack

    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            if (navController.showBottomBar) {
                BottomBar(
                    backStack = backStack,
                    onHomeClick = navController::navigateToHome,
                    onFavouritesClick = navController::navigateToFavourites
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = navController::navigateBack,
            modifier = Modifier.padding(innerPadding),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                movieEntries(
                    onNavigateToDetails = navController::navigateToDetails,
                )
                movieDetailsEntries(
                    onNavigateBack = navController::navigateBack
                )
                favouritesEntry(onNavigateToDetails = navController::navigateToDetails)
            }
        )
    }
}