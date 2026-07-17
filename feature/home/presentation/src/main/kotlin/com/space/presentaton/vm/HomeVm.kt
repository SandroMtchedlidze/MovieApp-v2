package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.space.domain.usecase.FilterUseCase
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeVm(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val movieUiMapper: MovieResponseToUiModel,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val filterUseCase: FilterUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<MovieCardUiModel>> = state
        .distinctUntilChanged { old, new ->
            old.searchQuery == new.searchQuery && old.selectedGenreId == new.selectedGenreId
        }
        .debounce(300.milliseconds).flatMapLatest { currentState ->
            when {
                currentState.searchQuery.isNotEmpty() -> {
                    searchMoviesUseCase(currentState.searchQuery)
                }

                currentState.selectedGenreId != null -> {
                    filterUseCase(currentState.selectedGenreId)
                }

                else -> {
                    getMoviesUseCase()
                }
            }
        }.map { pagingData ->
            pagingData.map { movieResponse ->
                movieUiMapper.mapToUiModel(movieResponse)
            }
        }.cachedIn(viewModelScope)

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnMovieClicked -> emitSideEffect(
                HomeSideEffect.NavigateToDetails(event.movieId)
            )

            is HomeEvent.OnSearchCleared -> {
                updateState { copy(searchQuery = "") }
            }

            is HomeEvent.OnSearchQueryChanged -> {
                updateState { copy(searchQuery = event.query) }
            }

            is HomeEvent.OnGenreSelected -> {
                val newId =
                    if (state.value.selectedGenreId == event.genreId) null else event.genreId
                updateState { copy(selectedGenreId = newId) }
            }

            is HomeEvent.OnFilterClicked -> {
                updateState { copy(isFilterVisible = !isFilterVisible) }
            }

            is HomeEvent.OnSearchFocusedChanged -> {
                updateState { copy(isSearchFocused = event.isFocused) }
            }
        }
    }

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { result ->
                if (result is ApiResult.Success) {
                    updateState {
                        copy(genres = result.data)
                    }
                }
            }
        }
    }
}