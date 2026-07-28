package com.space.movieapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.space.movieapp.ui.screen.MainActivity
import com.space.navigation.LocalGlobalNavigator
import com.space.navigation.rememberNavigator
import com.space.presentation.navigation.favouritesEntry
import com.space.presentation.navigation.movieDetailsEntry
import com.space.presentaton.navigation.homeEntry
import com.space.ui.theme.MovieAppTheme.colors

@Composable
fun MainActivity.MovieAppContainer(
    startDestination: NavKey,
    isOnline: Boolean
) {
    val navigator = rememberNavigator(startDestination)
    val backStack = navigator.backStack
    CompositionLocalProvider(LocalGlobalNavigator provides navigator) {

        Scaffold(
            containerColor = colors.background,
            bottomBar = { if (isOnline) BottomBar() }) { padding ->
            NavDisplay(
                backStack = backStack,
                modifier = Modifier.padding(padding),
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                onBack = { if (backStack.size > 1) navigator.pop() else finishAffinity() },
                entryProvider = entryProvider {
                    homeEntry()
                    favouritesEntry()
                    movieDetailsEntry()
                }
            )
        }
    }
}