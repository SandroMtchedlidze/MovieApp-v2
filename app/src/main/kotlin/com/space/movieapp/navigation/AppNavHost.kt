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

@Composable
fun AppNavHost() {
    val navigator = rememberNavigator(HomeRoute)

    CompositionLocalProvider(LocalGlobalNavigator provides navigator) {
        Scaffold(bottomBar = { BottomBar() }) { padding ->
            NavDisplay(
                modifier = Modifier.padding(padding),
                backStack = navigator.backStack,
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