package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetMoviesUseCase
import com.space.domain.usecase.SearchMoviesUseCase
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.mapper.MovieResponseToUiModel
import com.space.ui.component.MovieCardUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.milliseconds

class HomeVm(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val movieUiMapper: MovieResponseToUiModel,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {
    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<MovieCardUiModel>> = flow {
        getGenresUseCase().collect { result ->
            if (result is ApiResult.Success || result is ApiResult.Error) {
                emitAll(
                    searchQuery
                        .debounce(300.milliseconds)
                        .flatMapLatest { query ->
                            if (query.isEmpty()) {
                                getMoviesUseCase()
                            } else {
                                searchMoviesUseCase(query)
                            }
                        }
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

            is HomeEvent.OnSearchCleared -> {
                updateState { copy(searchQuery = "") }
                searchQuery.value = ""
            }

            is HomeEvent.OnSearchQueryChanged -> {
                updateState { copy(searchQuery = event.query) }
                searchQuery.value = event.query
            }
        }
    }
}