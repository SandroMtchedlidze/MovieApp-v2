package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetMoviesUseCase
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.mapper.MovieResponseToUiModel
import com.space.ui.component.MovieCardUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HomeVm(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val movieUiMapper: MovieResponseToUiModel
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {
    val movies: Flow<PagingData<MovieCardUiModel>> = flow {
        getGenresUseCase().collect { result ->
            if (result is ApiResult.Success || result is ApiResult.Error) {
                emitAll(
                    getMoviesUseCase()
                        .map { pagingData ->
                            pagingData.map { movieUiMapper.mapToUiModel(it) }
                        }
                )
            }
        }
    }.cachedIn(viewModelScope)

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnMovieClicked -> emitSideEffect(
                HomeSideEffect.NavigateToDetails(event.movieId)
            )
        }
    }
}