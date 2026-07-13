package com.space.movieapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
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
            TabWrapper(visible = navController.currentTab == AppTab.HOME) {
                NavDisplay(
                    backStack = navController.homeBackStack,
                    onBack = navController::navigateBack,
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        homeEntries(onNavigateToDetails = navController::navigateToDetails)
                        movieDetailsEntries(onNavigateBack = navController::navigateBack)
                    }
                )
            }
            TabWrapper(visible = navController.currentTab == AppTab.FAVOURITES) {
                NavDisplay(
                    backStack = navController.favouritesBackStack,
                    onBack = navController::navigateBack,
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        favouritesEntry(onNavigateToDetails = navController::navigateToDetails)
                        movieDetailsEntries(onNavigateBack = navController::navigateBack)
                    }
                )
            }
        }
    }
}

@Composable
private fun TabWrapper(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (visible) 1f else 0f)
            .then(
                if (visible) Modifier
                else Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(0, 0) {}
                    }
                    .pointerInput(Unit) {}
            )
    ) {
        content()
    }
}