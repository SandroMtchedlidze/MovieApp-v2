package com.space.movieapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.space.api.navigation.MovieDetailsRoute
import com.space.ui.theme.MovieAppTheme.colors

@Composable
fun MainScreen(isOnline: Boolean) {
    val navigator = rememberAppNavigator()
    val showBottomBar = navigator.backStack.lastOrNull() !is MovieDetailsRoute && isOnline

    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    backStack = navigator.backStack,
                    onHomeClick = navigator::toHome,
                    onFavouritesClick = navigator::toFavourites
                )
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navigator = navigator,
            modifier = Modifier.padding(innerPadding)
        )
    }
}