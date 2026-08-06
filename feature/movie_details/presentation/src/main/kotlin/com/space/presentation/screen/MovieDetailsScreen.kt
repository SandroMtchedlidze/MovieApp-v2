package com.space.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.space.core.ui.R
import com.space.presentation.base.NavigationCommandEffect
import com.space.presentation.base.rememberOnClick
import com.space.presentation.contract.MovieDetailsEvent
import com.space.presentation.contract.MovieDetailsState
import com.space.presentation.model.MovieDetailsUiModel
import com.space.presentation.vm.MovieDetailsVm
import com.space.ui.component.ErrorScreen
import com.space.ui.component.MovieappLoader
import com.space.ui.component.shimmerEffect
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Radius
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.space.movie.details.presentation.R as DetailsR

@Composable
fun MovieDetailsScreen(
    movieId: Int
) {
    val viewModel: MovieDetailsVm = koinViewModel { parametersOf(movieId) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    NavigationCommandEffect(viewModel)

    MovieDetailsScreenContent(
        state = state,
        onBackClick = { viewModel.onEvent(MovieDetailsEvent.OnBackClicked) },
        onFavouriteClick = {
            state.movie?.let {
                viewModel.onEvent(
                    MovieDetailsEvent.OnFavouriteClicked(
                        it
                    )
                )
            }
        },
        onRetryClick = { viewModel.onEvent(MovieDetailsEvent.OnRetryClicked) }
    )
}

@Composable
private fun MovieDetailsScreenContent(
    state: MovieDetailsState,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        DetailsTopBar(onBackClick = onBackClick)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> LoadingState()
                state.errorMessage != null -> ErrorScreen(
                    title = stringResource(DetailsR.string.something_went_wrong),
                    description = stringResource(state.errorMessage),
                    onRefreshClick = onRetryClick
                )

                else -> MovieDetailsBody(
                    movie = state.movie,
                    isFavourite = state.isFavourite,
                    onFavouriteClick = onFavouriteClick
                )
            }
        }
    }
}

@Composable
private fun DetailsTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Sizing.size4, vertical = Sizing.size8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = rememberOnClick { onBackClick() }) {
            Icon(
                modifier = Modifier.padding(start = Sizing.size12, top = Sizing.size10),
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = stringResource(DetailsR.string.back),
                tint = colors.onBackground
            )
        }
        Text(
            text = stringResource(DetailsR.string.details),
            style = typography.titleMedium,
            color = colors.onBackground,
            modifier = Modifier
                .weight(1f)
                .padding(top = Sizing.size10),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.width(Sizing.size42))
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        MovieappLoader(
            modifier = Modifier.align(Alignment.Center),
            mainColor = colors.primary,
            backgroundColor = colors.background
        )
    }
}

@Composable
private fun MovieDetailsBody(
    movie: MovieDetailsUiModel?,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit
) {
    val emptyText = stringResource(DetailsR.string.emptyText)

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.76f)
                .clip(Radius.radius16)
        ) {
            SubcomposeAsyncImage(
                model = movie?.posterUrl,
                contentDescription = movie?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                error = {
                    Image(
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                },
            )
        }
        Column(modifier = Modifier.padding(horizontal = Sizing.size16)) {
            Spacer(Modifier.height(Sizing.size16))
            MovieTitleRow(
                title = movie?.title ?: stringResource(DetailsR.string.unknownTitle),
                isFavourite = isFavourite,
                onFavouriteClick = onFavouriteClick
            )
            Spacer(Modifier.height(Sizing.size16))
            MovieInfoChips(
                ratingText = movie?.ratingText ?: emptyText,
                genreText = movie?.genreText ?: emptyText,
                runtimeText = movie?.runtimeText ?: emptyText,
                yearText = movie?.yearText ?: emptyText
            )
            Spacer(Modifier.height(Sizing.size26))
            Text(
                stringResource(DetailsR.string.about_movie),
                style = typography.titleMedium,
                color = colors.onBackground
            )
            Spacer(Modifier.height(Sizing.size8))
            Text(
                movie?.overviewText ?: emptyText,
                style = typography.bodyMedium,
                color = colors.onBackground
            )
            Spacer(Modifier.height(Sizing.size36))
        }
    }
}

@Composable
private fun MovieInfoChips(
    ratingText: String,
    genreText: String,
    runtimeText: String,
    yearText: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Sizing.size8),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        InfoChip(
            text = ratingText,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(Sizing.size16)
                )
                Spacer(Modifier.width(Sizing.size4))
            }
        )
        InfoChip(text = genreText)
        InfoChip(
            text = runtimeText,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.clock),
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(Sizing.size16)
                )
            }

        )
        InfoChip(text = yearText)
    }
}

@Composable
private fun MovieTitleRow(
    title: String,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = typography.titleLarge,
            color = colors.onBackground,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onFavouriteClick) {
            Icon(
                painter = painterResource(
                    if (isFavourite) R.drawable.detailschecked else R.drawable.detailsunchecked
                ),
                contentDescription = stringResource(DetailsR.string.favourites),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
private fun InfoChip(
    text: String,
    icon: (@Composable () -> Unit)? = null
) {
    if (text.isNotEmpty()) {
        Row(
            modifier = Modifier
                .background(color = colors.surface, shape = Radius.radius16)
                .padding(horizontal = Spacing.spacing10, vertical = Spacing.spacing4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing4)
        ) {
            if (icon != null) icon()

            Text(
                text = text,
                style = typography.bodyMedium,
                color = colors.textSecondary
            )
        }
    }
}