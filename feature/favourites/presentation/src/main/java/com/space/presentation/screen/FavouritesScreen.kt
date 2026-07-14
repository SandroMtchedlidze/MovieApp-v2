package com.space.presentation.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.space.presentation.contract.FavouritesEffect
import com.space.presentation.contract.FavouritesEvent
import com.space.presentation.vm.FavouritesVm
import com.space.ui.component.MovieCard
import com.space.ui.component.MovieCardUiModel
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouritesScreen(
    onNavigateToDetails: (movieId: Int) -> Unit,
    viewmodel: FavouritesVm = koinViewModel()   //wavshalo tu ar vcvli arsad cvladad mqondes.
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.sideEffect.collect { effect ->
            when (effect) {
                is FavouritesEffect.NavigateToDetails -> onNavigateToDetails(effect.movieId)
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            stringResource(com.space.favourites.presentation.R.string.favorite_movies),
            style = typography.titleMedium,
            color = colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = Sizing.size2)
                .align(Alignment.CenterHorizontally)
        )
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            state.favourites.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(com.space.favourites.presentation.R.drawable.empty),
                        tint = colors.border,
                        contentDescription = null
                    )
                    Spacer(Modifier.height(Sizing.size26))
                    Text(
                        stringResource(com.space.favourites.presentation.R.string.no_movies_added_yet),
                        style = typography.titleMedium,
                        color = colors.border
                    )
                }
            }

            else -> {
                FavouritesGrid(
                    movies = state.favourites,
                    onMovieClicked = { movieId ->
                        viewmodel.onEvent(
                            FavouritesEvent.OnMovieClicked(
                                movieId
                            )
                        )
                    },
                    onFavouriteClicked = { movie ->
                        viewmodel.onEvent(
                            FavouritesEvent.OnFavouriteToggle(
                                movie
                            )
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun FavouritesGrid(
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
                onClick = onMovieClicked,
                onFavouriteClick = { onFavouriteClicked(movie) }
            )
        }
    }
}