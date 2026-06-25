package com.space.presentaton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.vm.HomeVm
import com.space.ui.component.MovieGrid
import com.space.ui.theme.MovieAppTheme.colors
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(
    viewModel: HomeVm = koinViewModel(),
    onMovieClicked: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToDetails -> onMovieClicked(sideEffect.movieId)
            }
        }
    }
    MovieScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun MovieScreenContent(
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
                    movies = state.movies,
                    onMovieClick = { onEvent(HomeEvent.OnHomeClicked(it)) },
                    onFavouriteClick = {}
                )
            }
        }
    }
}