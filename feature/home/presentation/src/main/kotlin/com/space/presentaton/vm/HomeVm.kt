package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.space.database.network_observer.ConnectivityObserver
import com.space.domain.model.MovieResponse
import com.space.domain.usecase.GetAllFavouritesIdsUseCase
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentaton.contract.HomeEvent
import com.space.presentaton.contract.HomeSideEffect
import com.space.presentaton.contract.HomeState
import com.space.presentaton.mapper.MovieResponseToUiModel
import com.space.presentaton.mapper.MovieUiModelToDomain
import com.space.presentaton.provider.ProvideHomeUseCase
import com.space.ui.component.MovieCardUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeVm(
    private val provideHomeUseCase: ProvideHomeUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getAllFavouritesIdsUseCase: GetAllFavouritesIdsUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val movieUiMapper: MovieResponseToUiModel,
    private val networkObserver: ConnectivityObserver,
    private val mapperToDomain: MovieUiModelToDomain
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {
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
                        copy(genres = result.data, isLoading = false)
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private val searchQueryFlow = state
        .map { it.searchQuery }
        .debounce(SEARCH_DEBOUNCE.milliseconds)
        .distinctUntilChanged()

    private val genreIdFlow = state
        .map { it.selectedGenreId }
        .distinctUntilChanged()

    private val genresLoadedFlow = state
        .map { !it.isLoading }
        .distinctUntilChanged()

    private val favouriteIdsFlow = getAllFavouritesIdsUseCase()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val combinePagingFlow: Flow<PagingData<MovieResponse>> = combine(
        genresLoadedFlow, searchQueryFlow, genreIdFlow,
    ) { genresLoaded, searchQuery, genreId ->
        if (genresLoaded) searchQuery to genreId else null
    }.filterNotNull()
        .distinctUntilChanged()
        .flatMapLatest { (searchQuery, genreId) -> provideHomeUseCase(searchQuery, genreId) }
        .cachedIn(viewModelScope)

    val moviesPagingFlow: Flow<PagingData<MovieCardUiModel>> = combine(
        combinePagingFlow, favouriteIdsFlow
    ) { pagingData, favouriteId ->
        pagingData.map { movie ->
            movieUiMapper.mapToUiModel(movie).copy(isFavourite = favouriteId.contains(movie.id))
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkObserver.observe().collectLatest { connected ->
                updateState { copy(isConnected = connected) }
            }
        }
    }

    init {
        loadGenres()
        observeNetwork()
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 300L
    }
}