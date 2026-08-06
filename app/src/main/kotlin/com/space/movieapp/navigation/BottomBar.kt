package com.space.movieapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.space.core.ui.R
import com.space.navigation.requireGlobalNavigator
import com.space.presentation.base.rememberOnClick
import com.space.ui.component.NavButton
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.Spacing

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
) {
    val navigator = requireGlobalNavigator()
    if (!navigator.showBottomBar) return
    Surface(color = colors.background, modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing16, vertical = Spacing.spacing12),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing12)
        ) {
            NavButton(
                selected = navigator.currentTab == AppTab.HOME,
                label = stringResource(com.space.movieapp.R.string.home),
                iconResId = R.drawable.home,
                modifier = Modifier.weight(1f),
                onClick = rememberOnClick { navigator.navigateToHome() }
            )
            NavButton(
                selected = navigator.currentTab == AppTab.FAVOURITES,
                label = stringResource(com.space.movieapp.R.string.favorites),
                iconResId = R.drawable.heart,
                modifier = Modifier.weight(1f),
                onClick = rememberOnClick { navigator.navigateToFavourites() }
            )
        }
    }
}