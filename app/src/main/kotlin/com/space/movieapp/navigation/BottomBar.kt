package com.space.movieapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.space.api.navigation.FavouritesRoute
import com.space.api.navigation.MovieRoute
import com.space.core.ui.R
import com.space.ui.component.NavButton
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.Spacing

@Composable
fun BottomBar(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onFavouritesClick: () -> Unit
) {
    val currRoute = backStack.lastOrNull()
    Surface(color = colors.background, modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing16, vertical = Spacing.spacing12),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing12)
        ) {
            NavButton(
                selected = currRoute == MovieRoute,
                label = "Home",
                iconResId = R.drawable.home,
                modifier = Modifier.weight(1f),
                onClick = onHomeClick
            )
            NavButton(
                selected = currRoute == FavouritesRoute,
                label = "Favorites",
                iconResId = R.drawable.heart,
                modifier = Modifier.weight(1f),
                onClick = onFavouritesClick
            )
        }
    }
}
