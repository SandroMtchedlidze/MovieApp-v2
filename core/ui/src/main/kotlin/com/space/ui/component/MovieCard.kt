package com.space.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.SubcomposeAsyncImage
import com.space.core.ui.R
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Radius
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import com.space.ui.theme.TextSizing

/**
 * Movie grid for home page with 2 columns.
 * Displays movie image , genre , title, release date.
 * Has favourite button which adds movie to favourite page.
 *
 * @param movie takes movie model from api.
 * @param modifier to be applied to the root of the composable.
 * @param onClick takes user to details page.
 * @param onFavouriteClick marks movie as favourite.
 */
@Composable
fun MovieCard(
    movie: MovieCardUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(Radius.radius16)
                .clickable { onClick() }
        ) {
            SubcomposeAsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )
            if (movie.genre.isNotEmpty()) {
                Text(
                    text = movie.genre,
                    color = colors.background,
                    style = typography.titleMedium.copy(
                        fontSize = TextSizing.size10,
                        lineHeight = TextSizing.size14,
                        letterSpacing = TextSizing.size1
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = Spacing.spacing10, end = Spacing.spacing12)
                        .clip(Radius.radius22)
                        .background(colors.primary)
                        .padding(
                            top = Spacing.spacing4,
                            start = Spacing.spacing12,
                            end = Spacing.spacing12, bottom = Spacing.spacing4
                        )
                )
            }
        }
        Spacer(Modifier.height(Spacing.spacing12))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = typography.bodyMedium.copy(letterSpacing = TextSizing.size1),
                    color = colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Spacing.spacing2))
                Text(
                    text = movie.releaseDate,
                    style = typography.titleSmall,
                    color = colors.textHint,
                )
            }
            IconButton(
                onClick = { onFavouriteClick() },
                modifier = Modifier.size(Sizing.size20)
            ) {
                Icon(
                    painter = if (movie.isFavourite) painterResource(R.drawable.homechecked) else painterResource(
                        R.drawable.homeunchecked
                    ),
                    contentDescription = if (movie.isFavourite)
                        stringResource(R.string.remove_from_favourites) else stringResource(
                        R.string.add_to_favourites
                    ),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

data class MovieCardUiModel(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genre: String,
    val posterUrl: String,
    val isFavourite: Boolean = false
)