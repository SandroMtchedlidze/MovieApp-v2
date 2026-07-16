package com.space.movieapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.space.api.navigation.HomeRoute
import com.space.navigation.LocalGlobalNavigator
import com.space.navigation.rememberNavigator
import com.space.presentation.navigation.favouritesEntry
import com.space.presentation.navigation.movieDetailsEntry
import com.space.presentaton.navigation.homeEntry
import com.space.ui.theme.MovieAppTheme.colors

@Composable
fun AppNavHost() {
    val navigator = rememberNavigator(HomeRoute)
    val backStack = navigator.backStack
    CompositionLocalProvider(LocalGlobalNavigator provides navigator) {
        Scaffold(containerColor = colors.background, bottomBar = { BottomBar() }) { padding ->
            NavDisplay(
                backStack = backStack,
                modifier = Modifier.padding(padding),
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                onBack = { navigator.pop() },
                entryProvider = entryProvider {
                    homeEntry()
                    favouritesEntry()
                    movieDetailsEntry()
                }
            )
        }
    }
}