package com.space.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.space.presentation.base.NavigationCommandEffect
import com.space.presentation.contract.FavouritesEvent
import com.space.presentation.contract.FavouritesState
import com.space.presentation.vm.FavouritesVm
import com.space.ui.component.ErrorScreen
import com.space.ui.component.MovieCard
import com.space.ui.component.MovieCardUiModel
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel
import com.space.favourites.presentation.R as FavouritesR

@Composable
fun FavouritesScreen() {

    val viewmodel: FavouritesVm = koinViewModel()
    val state by viewmodel.state.collectAsStateWithLifecycle()

    NavigationCommandEffect(viewmodel)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        FavouriteScreenContent(
            state = state,
            onMovieClicked = { movieId ->
                viewmodel.onEvent(FavouritesEvent.OnMovieClicked(movieId))
            },
            onFavouriteClicked = { movie ->
                viewmodel.onEvent(FavouritesEvent.OnFavouriteToggle(movie))
            },
            onRetry = { viewmodel.onEvent(FavouritesEvent.OnRetryClicked) }
        )
    }
}

@Composable
private fun FavouriteScreenContent(
    state: FavouritesState,
    onMovieClicked: (Int) -> Unit,
    onRetry: () -> Unit,
    onFavouriteClicked: (MovieCardUiModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            stringResource(FavouritesR.string.favorite_movies),
            style = typography.titleMedium,
            color = colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = Sizing.size2)
                .align(Alignment.CenterHorizontally)
        )
        when {
            state.isLoading -> LoadingState()
            state.favourites.isEmpty() -> EmptyFavouritesState()
            state.error != null -> ErrorScreen(
                title = stringResource(FavouritesR.string.something_went_wrong),
                description = state.error,
                onRefreshClick = onRetry
            )

            else -> FavouritesGrid(
                movies = state.favourites,
                onMovieClicked = onMovieClicked,
                onFavouriteClicked = onFavouriteClicked,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyFavouritesState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(FavouritesR.drawable.empty),
            tint = colors.border,
            contentDescription = null
        )
        Spacer(Modifier.height(Sizing.size26))
        Text(
            stringResource(FavouritesR.string.no_movies_added_yet),
            style = typography.titleMedium,
            color = colors.border
        )
    }
}

@Composable
private fun FavouritesGrid(
    movies: List<MovieCardUiModel>,
    onMovieClicked: (Int) -> Unit,
    onFavouriteClicked: (MovieCardUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing16),
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing22),
        contentPadding = PaddingValues(Spacing.spacing16),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieCard(
                movie = movie,
                onClick = { onMovieClicked(movie.id) },
                onFavouriteClick = { onFavouriteClicked(movie) }
            )
        }
    }
}
