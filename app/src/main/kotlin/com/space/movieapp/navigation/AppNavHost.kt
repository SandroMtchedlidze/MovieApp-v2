package com.space.movieapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.space.api.navigation.favouritesEntry
import com.space.api.navigation.homeEntries
import com.space.api.navigation.movieDetailsEntries
import com.space.ui.theme.MovieAppTheme.colors

@Composable
fun AppNavHost() {
    val navController = rememberAppNavController()

    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            if (navController.showBottomBar) {
                BottomBar(
                    currentTab = navController.currentTab,
                    onHomeClick = navController::navigateToHome,
                    onFavouritesClick = navController::navigateToFavourites
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavDisplay(
                backStack = navController.backStack,
                onBack = navController::navigateBack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                entryProvider = entryProvider {
                    homeEntries(onNavigateToDetails = navController::navigateToDetails)
                    movieDetailsEntries(onNavigateBack = navController::navigateBack)
                    favouritesEntry(onNavigateToDetails = navController::navigateToDetails)
                }
            )
        }
    }
}