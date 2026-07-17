package com.space.presentaton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.vm.HomeVm
import com.space.ui.component.MovieCard
import com.space.ui.component.MovieCardUiModel
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(
    viewModel: HomeVm = koinViewModel(),
    onMovieClicked: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies = viewModel.movies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToDetails -> onMovieClicked(sideEffect.movieId)
            }
        }
    }
    MovieScreenContent(movies = movies, state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun MovieScreenContent(
    movies: LazyPagingItems<MovieCardUiModel>,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colors.primary
                )
            }

            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage
                )
            }

            else -> {
                MovieGrid(
                    movies = movies,
                    onMovieClicked = { onEvent(HomeEvent.OnMovieClicked(it)) },
                    onFavouriteClicked = { }
                )
            }
        }
    }
}

@Composable
private fun MovieGrid(
    movies: LazyPagingItems<MovieCardUiModel>,
    onMovieClicked: (Int) -> Unit,
    onFavouriteClicked: (Int) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing16),
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing22),
        contentPadding = PaddingValues(Spacing.spacing16),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = movies.itemCount,
        ) { index ->
            movies[index]?.let { movie ->
                MovieCard(
                    movie = movie,
                    onClick = onMovieClicked,
                    onFavouriteClick = onFavouriteClicked
                )
            }
        }
    }
}