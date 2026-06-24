package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetTopRatedMoviesUseCase
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentaton.contract.MovieEvent
import com.space.presentaton.contract.MovieSideEffect
import com.space.presentaton.contract.MovieState
import com.space.presentaton.mapper.toUiModel
import kotlinx.coroutines.launch

class MovieVm(
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
            getGenresUseCase().collect { result ->
                if (result is ApiResult.Success || result is ApiResult.Error) {
                    getTopRatedMoviesUseCase().collect { movieResult ->
                        when (movieResult) {
                            is ApiResult.Loading -> updateState {
                                copy(isLoading = movieResult.isLoading)
                            }

                            is ApiResult.Success -> updateState {
                                copy(
                                    isLoading = false,
                                    movies = movieResult.data.map { it.toUiModel() })
                            }

                            is ApiResult.Error -> updateState {
                                copy(
                                    isLoading = false,
                                    errorMessage = movieResult.networkError.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}