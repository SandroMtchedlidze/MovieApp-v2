package com.space.presentaton.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.vm.HomeVm
import com.space.ui.component.GenreRow
import com.space.ui.component.MovieCard
import com.space.ui.component.MovieCardUiModel
import com.space.ui.component.SearchField
import com.space.ui.component.isScrollingUp
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Spacing
import com.space.ui.theme.TextSizing
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(
    viewModel: HomeVm = koinViewModel(),
    onMovieClicked: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val merged = state.movies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToDetails -> onMovieClicked(sideEffect.movieId)
            }
        }
    }
    MovieScreenContent(movies = merged, state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun MovieScreenContent(
    movies: LazyPagingItems<MovieCardUiModel>,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    val gridState = rememberLazyGridState()
    val isScrollingUp by gridState.isScrollingUp()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)

    ) {
        AnimatedVisibility(visible = isScrollingUp) {
            SearchAndFilterHeader(state = state, onEvent = onEvent)
        }
        MoviesResultSection(movies = movies, gridState = gridState, onEvent = onEvent)
    }
}

/**
 * Displays grid , takes paging items as argument grid state to observe scrolling.
 * @param onMovieClicked to navigate to details screen.
 * @param onFavouriteClicked to mark movie as favourite.
 */
@Composable
private fun MovieGrid(
    movies: LazyPagingItems<MovieCardUiModel>,
    gridState: LazyGridState,
    onMovieClicked: (Int) -> Unit,
    onFavouriteClicked: (MovieCardUiModel) -> Unit
) {
    LazyVerticalGrid(
        state = gridState,
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
                    onClick = { onMovieClicked(movie.id) },
                    onFavouriteClick = { onFavouriteClicked(movie) }
                )
            }
        }
        if (movies.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(2) }) {
                AppendLoadingIndicator()
            }
        }
    }
}

/**
 * Displays search field and filter.
 */
@Composable
private fun SearchAndFilterHeader(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Column {
        SearchField(
            query = state.searchQuery,
            isFilterActive = state.isFilterVisible,
            isFocused = state.isSearchFocused,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing16)
                .padding(top = Spacing.spacing16),
            onQueryChanged = { onEvent(HomeEvent.OnSearchQueryChanged(it)) },
            onCancelClicked = { onEvent(HomeEvent.OnSearchCleared) },
            onFilterClicked = { onEvent(HomeEvent.OnFilterClicked) },
            onFocusChanged = { onEvent(HomeEvent.OnSearchFocusedChanged(it)) }
        )
        AnimatedVisibility(
            visible = state.isFilterVisible,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
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
            text = "Movies",
            style = typography.titleLarge.copy(
                letterSpacing = TextSizing.size1,
                fontSize = TextSizing.size18,
                lineHeight = TextSizing.size18
            ),
            color = colors.primary,
            modifier = Modifier.padding(horizontal = Spacing.spacing16)
        )
    }
}

/**
 * Displays movies or shows loading indicator or error if movies failed.
 */
@Composable
private fun MoviesResultSection(
    movies: LazyPagingItems<MovieCardUiModel>,
    gridState: LazyGridState,
    onEvent: (HomeEvent) -> Unit,
) {
    when (movies.loadState.refresh) {
        is LoadState.Loading -> FullScreenLoading()
        is LoadState.Error -> FullScreenError(message = "Something went wrong")
        else -> MovieGrid(
            movies = movies,
            gridState = gridState,
            onMovieClicked = { onEvent(HomeEvent.OnMovieClicked(it)) },
            onFavouriteClicked = { movie -> onEvent(HomeEvent.OnFavouriteClicked(movie)) }
        )
    }
}

@Composable
private fun FullScreenLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = colors.primary
        )
    }
}

@Composable
private fun FullScreenError(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            color = colors.primary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun AppendLoadingIndicator() {
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