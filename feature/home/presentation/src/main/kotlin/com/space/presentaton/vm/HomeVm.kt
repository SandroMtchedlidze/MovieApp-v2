package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.space.domain.model.MovieResponse
import com.space.domain.usecase.GetAllFavouritesIdsUseCase
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.HomeMoviesUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.mapper.MovieResponseToUiModel
import com.space.presentaton.mapper.MovieUiModelToDomain
import com.space.ui.component.MovieCardUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeVm(
    private val homeMoviesUseCase: HomeMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getAllFavouritesIdsUseCase: GetAllFavouritesIdsUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val movieUiMapper: MovieResponseToUiModel,
    private val mapperToDomain: MovieUiModelToDomain
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {
    init {
        loadGenres()
        updateState { copy(movies = buildMoviesFlow()) }
    }

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

            is HomeEvent.OnFavouriteClicked -> {
                viewModelScope.launch {
                    val domainMovie = mapperToDomain.uiModelToDomain(event.movie)
                    toggleFavouriteUseCase(domainMovie)
                }
            }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { result ->
                if (result is ApiResult.Success) {
                    updateState {
                        copy(genres = result.data, genresLoaded = true)
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun buildMoviesFlow(): Flow<PagingData<MovieCardUiModel>> =
        observeRelevantState()
            .debounce(300.milliseconds)
            .flatMapLatest { resolveMovieSource(it) }
            .map { mapToUiModels(it) }
            .combineWithFavouriteStatus()
            .cachedIn(viewModelScope)

    /**
     * Emits new state when search query changes or
     * selected genre id changes or genres are loaded
     */
    private fun observeRelevantState(): Flow<HomeState> =
        state.distinctUntilChanged { old, new ->
            old.searchQuery == new.searchQuery &&
                    old.selectedGenreId == new.selectedGenreId &&
                    old.genresLoaded == new.genresLoaded
        }

    /**
     * decides what to display
     * if search query is not empty it fetches from searchApi,
     * if genre is selected it filters
     * if nothing is selected or searched it fetches movies
     */
    private fun resolveMovieSource(currentState: HomeState): Flow<PagingData<MovieResponse>> {
        if (!currentState.genresLoaded) return flowOf(PagingData.empty())

        return homeMoviesUseCase(currentState.searchQuery, currentState.selectedGenreId)
            .cachedIn(viewModelScope)
    }

    /**
     * Maps Domain model into Ui model
     */
    private fun mapToUiModels(pagingData: PagingData<MovieResponse>): PagingData<MovieCardUiModel> =
        pagingData.map { movieUiMapper.mapToUiModel(it) }


    /**
     * Checks each movie if it is in database when displaying heart icon is checked
     */
    private fun Flow<PagingData<MovieCardUiModel>>.combineWithFavouriteStatus() =
        combine(getAllFavouritesIdsUseCase()) { pagingData, favouriteIds ->
            pagingData.map { it.copy(isFavourite = favouriteIds.contains(it.id)) }
        }
}