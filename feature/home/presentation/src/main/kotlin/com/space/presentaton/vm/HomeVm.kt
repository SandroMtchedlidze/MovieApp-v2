package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetTopRatedMoviesUseCase
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.mapper.MovieResponseToUiModel
import kotlinx.coroutines.launch

class HomeVm(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val movieUiMapper: MovieResponseToUiModel
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {
    init {
        onEvent(HomeEvent.LoadMovies)
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadMovies -> loadMovies()
            is HomeEvent.OnHomeClicked -> emitSideEffect(
                HomeSideEffect.NavigateToDetails(event.movieId)
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
                                    movies = movieResult.data.map { movieUiMapper.mapToUiModel(it) })
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