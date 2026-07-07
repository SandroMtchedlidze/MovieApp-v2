package com.space.presentation.screen

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import com.space.core.ui.R
import com.space.presentation.contract.MovieDetailsEvent
import com.space.presentation.contract.MovieDetailsSideEffect
import com.space.presentation.vm.MovieDetailsVm
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Radius
import com.space.ui.theme.Sizing
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

//rame sxva vipovo
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onNavigateBack: () -> Unit,
    viewModel: MovieDetailsVm = koinViewModel(key = "DetailsVm-$movieId") {
        parametersOf(
            movieId
        )
    }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is MovieDetailsSideEffect.NavigateToBack -> onNavigateBack()
            }
        }
    }
    MovieDetailsScreenContent(
        posterUrl = state.movie?.posterUrl,
        title = state.movie?.title ?: stringResource
            (com.space.movie.details.presentation.R.string.unknownTitle),
        ratingText = state.movie?.ratingText ?: stringResource
            (com.space.movie.details.presentation.R.string.emptyText),
        genreText = state.movie?.genreText ?: stringResource
            (com.space.movie.details.presentation.R.string.emptyText),
        runtimeText = state.movie?.runtimeText ?: stringResource
            (com.space.movie.details.presentation.R.string.emptyText),
        yearText = state.movie?.yearText ?: stringResource
            (com.space.movie.details.presentation.R.string.emptyText),
        overviewText = state.movie?.overviewText ?: stringResource
            (com.space.movie.details.presentation.R.string.overview),
        isFavorite = state.isFavourite,
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        onBackClick = { viewModel.onEvent(MovieDetailsEvent.OnBackClicked) },
        onFavoriteClick = {
            state.movie?.let { currentMovie ->
                viewModel.onEvent(MovieDetailsEvent.OnFavouriteClicked(currentMovie))
            }
        },
        onRetryClick = { viewModel.onEvent(MovieDetailsEvent.OnRetryClicked) }
    )
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
    isLoading: Boolean,
    errorMessage: String?,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Sizing.size4, vertical = Sizing.size8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    modifier = Modifier.padding(start = Sizing.size12, top = Sizing.size10),
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = stringResource
                        (com.space.movie.details.presentation.R.string.back),
                    tint = colors.onBackground
                )
            }
            Text(
                text = stringResource(com.space.movie.details.presentation.R.string.details),
                style = typography.titleMedium,
                color = colors.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = Sizing.size10),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.width(Sizing.size42))
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Sizing.size36),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = colors.primary)
                    }
                }

                errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Sizing.size16),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            errorMessage,
                            style = typography.bodyMedium,
                            color = colors.onBackground
                        )
                        Spacer(Modifier.height(Sizing.size8))
                        Button(onClick = onRetryClick)
                        { Text(text = stringResource(com.space.movie.details.presentation.R.string.retry)) }
                    }
                }

                else -> {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.76f)
                                .clip(Radius.radius16)
                        ) {
                            AsyncImage(
                                model = posterUrl,
                                contentDescription = title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(modifier = Modifier.padding(horizontal = Sizing.size16)) {
                            Spacer(Modifier.height(Sizing.size16))
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
                                IconButton(onClick = onFavoriteClick) {
                                    Icon(
                                        painter = if (isFavorite) painterResource(R.drawable.detailschecked) else painterResource(
                                            R.drawable.detailsunchecked
                                        ),
                                        contentDescription = stringResource
                                            (com.space.movie.details.presentation.R.string.favourites),
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            Spacer(Modifier.height(Sizing.size16))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Sizing.size8),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                            ) {
                                InfoChip {
                                    Icon(
                                        painter = painterResource(R.drawable.star),
                                        contentDescription = null,
                                        tint = colors.primary,
                                        modifier = Modifier.size(Sizing.size16)
                                    )
                                    Spacer(Modifier.width(Sizing.size4))
                                    Text(
                                        ratingText,
                                        style = typography.bodyMedium,
                                        color = colors.textTertiary
                                    )
                                }
                                InfoChip {
                                    Text(
                                        genreText,
                                        style = typography.bodyMedium,
                                        color = colors.textTertiary
                                    )
                                }
                                InfoChip {
                                    Icon(
                                        painter = painterResource(R.drawable.clock),
                                        contentDescription = null,
                                        tint = colors.primary,
                                        modifier = Modifier.size(Sizing.size16)
                                    )
                                    Spacer(Modifier.width(Sizing.size4))
                                    Text(
                                        runtimeText,
                                        style = typography.bodyMedium,
                                        color = colors.textTertiary
                                    )
                                }
                                InfoChip {
                                    Text(
                                        yearText,
                                        style = typography.bodyMedium,
                                        color = colors.textTertiary
                                    )
                                }
                            }
                            Spacer(Modifier.height(Sizing.size26))
                            Text(
                                stringResource(com.space.movie.details.presentation.R.string.about_movie),
                                style = typography.titleMedium,
                                color = colors.onBackground
                            )
                            Spacer(Modifier.height(Sizing.size8))
                            Text(
                                overviewText,
                                style = typography.bodyMedium,
                                color = colors.onBackground
                            )
                            Spacer(Modifier.height(Sizing.size36))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoChip(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .clip(Radius.radius16)
            .background(colors.surface)
            .padding(horizontal = Sizing.size12, vertical = Sizing.size6),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        content()
    }
}