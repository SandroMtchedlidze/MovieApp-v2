package com.space.presentaton.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.space.presentaton.R
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.vm.HomeVm
import com.space.ui.component.GenreRow
import com.space.ui.component.MovieCard
import com.space.ui.component.MovieCardUiModel
import com.space.ui.component.SearchField
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import com.space.ui.theme.TextSizing
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .padding(top = Sizing.size22)
    ) {
        SearchField(
            query = state.searchQuery,
            isFilterActive = state.isFilterVisible,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing16),
            onQueryChanged = { onEvent(HomeEvent.OnSearchQueryChanged(it)) },
            onCancelClicked = {
                onEvent(HomeEvent.OnSearchCleared)
            },
            onFilterClicked = { onEvent(HomeEvent.OnFilterClicked) }
        )
        AnimatedVisibility(
            visible = state.isFilterVisible,
        ) {
            Column {
                Spacer(Modifier.height(Spacing.spacing12))
                GenreRow(
                    genres = state.genres,
                    selectedGenreId = state.selectedGenreId,
                    onGenreSelected = { onEvent(HomeEvent.OnGenreSelected(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(Modifier.height(Spacing.spacing16))
        Text(
            text = stringResource(R.string.movies),
            style = typography.titleLarge.copy(
                letterSpacing = TextSizing.size1,
                fontSize = TextSizing.size18, lineHeight = TextSizing.size18
            ),
            color = colors.primary,
            modifier = Modifier.padding(horizontal = Spacing.spacing16)
        )
        when (movies.loadState.refresh) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colors.primary
                    )
                }
            }

            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.something_went_wrong),
                        color = colors.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
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
        if (movies.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.spacing16)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colors.primary
                    )
                }
            }
        }
    }
}