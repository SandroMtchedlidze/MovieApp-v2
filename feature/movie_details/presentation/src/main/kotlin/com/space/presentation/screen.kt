package com.space.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Sizing

@Composable
fun MovieDetailsScreen() {

}

@Composable
private fun MovieDetailsScreenContent(
    posterUrl: String?,
    title: String,
    ratingText: String,
    genreText: String,
    runtimeText: String,
    yearText: String,
    overviewText: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.8f)
        ) {
            AsyncImage(
                model = posterUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = Sizing.size4, start = Sizing.size4),
                onClick = onBackClick

            ) {
                Icon(
                    painter = painterResource(com.space.core.ui.R.drawable.back_arrow),
                    contentDescription = stringResource(com.space.core.ui.R.string.Back),
                    tint = colors.onBackground
                )
            }
        }
        Column(modifier = Modifier.padding(horizontal = Sizing.size16)) {
            Spacer(Modifier.height(Sizing.size16))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = typography.titleLarge,
                    color = colors.onBackground
                )
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        painter = painterResource(com.space.core.ui.R.drawable.favourite),
                        contentDescription = stringResource(com.space.core.ui.R.string.Back),
                        tint = if (isFavorite) colors.primary else Color.Unspecified
                    )
                }
            }
            Spacer(Modifier.height(Sizing.size16))
            Row(horizontalArrangement = Arrangement.spacedBy(Sizing.size8)) {

            }
        }
    }
}