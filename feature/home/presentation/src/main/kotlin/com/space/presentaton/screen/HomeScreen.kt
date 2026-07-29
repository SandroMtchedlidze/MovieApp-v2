package com.space.presentaton.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.space.navigation.requireGlobalNavigator
import com.space.networking.network.NetworkError
import com.space.networking.network.PagingException
import com.space.presentation.base.getErrorStrings
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.vm.HomeVm
import com.space.ui.component.EmptyResultView
import com.space.ui.component.ErrorScreen
import com.space.ui.component.GenreRow
import com.space.ui.component.MovieCard
import com.space.ui.component.MovieCardUiModel
import com.space.ui.component.MovieappLoader
import com.space.ui.component.NetworkStatusBanner
import com.space.ui.component.SearchField
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Spacing
import com.space.ui.theme.TextSizing
import org.koin.androidx.compose.koinViewModel
import com.space.home.presentaton.R as HomeR

@Composable
fun HomeScreen() {

    val viewModel: HomeVm = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies = viewModel.moviesPagingFlow.collectAsLazyPagingItems()
    val navigator = requireGlobalNavigator()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.Navigate -> sideEffect.command.execute(navigator)
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
    val gridState = rememberLazyGridState()

    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        snapshotFlow { state.hasInternetConnection to movies.loadState.append }
            .collect { (isConnected, appendState) ->
                if (isConnected && appendState is LoadState.Error) {
                    movies.retry()
                }
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .background(colors.background)
    ) {
        SearchAndFilterHeader(state = state, onEvent = onEvent)
        when (movies.loadState.refresh) {
            is LoadState.Loading -> FullScreenLoading()
            is LoadState.NotLoading if movies.itemCount == 0 -> {
                EmptyResultView()
            }

            is LoadState.Error -> {
                val exception =
                    (movies.loadState.refresh as LoadState.Error).error as? PagingException
                val descriptionRes =
                    getErrorStrings(exception?.errorType ?: NetworkError.UNKNOWN)
                ErrorScreen(
                    title = stringResource(HomeR.string.data_can_t_be_loaded),
                    description = stringResource(descriptionRes),
                    onRefreshClick = { movies.retry() }
                )
            }

            else -> {
                MovieGrid(
                    movies = movies,
                    gridState = gridState,
                    isConnected = state.hasInternetConnection,
                    onMovieClicked = { onEvent(HomeEvent.OnMovieClicked(it)) },
                    onFavouriteClicked = { movie -> onEvent(HomeEvent.OnFavouriteClicked(movie)) }
                )
            }
        }
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
    isConnected: Boolean,
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
                if (isConnected) AppendLoadingIndicator()
            }
        }
        if (!isConnected) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                NetworkStatusBanner(isConnected = isConnected)
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
            text = stringResource(HomeR.string.movies),
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

@Composable
private fun FullScreenLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        MovieappLoader(
            modifier = Modifier.align(Alignment.Center),
            mainColor = colors.primary,
            backgroundColor = colors.background
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