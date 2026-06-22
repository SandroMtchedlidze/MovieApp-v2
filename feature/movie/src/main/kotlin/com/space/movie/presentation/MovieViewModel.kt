package com.space.movie.presentation

import androidx.lifecycle.viewModelScope
import com.space.common.base.BaseViewModel
import com.space.common.network.ApiResult
import com.space.movie.domain.usecase.GetGenresUseCase
import com.space.movie.domain.usecase.GetTopRatedMoviesUseCase
import com.space.movie.presentation.contract.MovieEvent
import com.space.movie.presentation.contract.MovieSideEffect
import com.space.movie.presentation.contract.MovieState
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase
) : BaseViewModel<MovieState, MovieEvent, MovieSideEffect>(
    initialState = MovieState()
) {
    init {
        onEvent(MovieEvent.LoadMovies)
    }

    override fun onEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.LoadMovies -> loadMovies()
            is MovieEvent.OnMovieClicked -> emitSideEffect(
                MovieSideEffect.NavigateToDetails(event.movieId)
            )
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

            getGenresUseCase()
            when (val result = getTopRatedMoviesUseCase()) {
                is ApiResult.Success -> updateState {
                    copy(
                        isLoading = false,
                        movies = result.data
                    )
                }

                is ApiResult.Error -> updateState {
                    copy(isLoading = false, errorMessage = result.message)
                }

                is ApiResult.Loading -> updateState {
                    copy(isLoading = true)
                }
            }
        }
    }
}