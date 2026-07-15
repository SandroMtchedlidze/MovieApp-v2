package com.space.presentaton.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.space.api.navigation.MovieDetailsRoute
import com.space.database.network_observer.ConnectivityObserver
import com.space.domain.usecase.FilterUseCase
import com.space.domain.usecase.GetAllFavouritesUseCase
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetMoviesUseCase
import com.space.domain.usecase.SearchMoviesUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import com.space.navigation.NavigationCommand
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentation.base.getErrorStrings
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
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val movieUiMapper: MovieResponseToUiModel,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val filterUseCase: FilterUseCase,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val mapperToDomain: MovieUiModelToDomain,
    private val connectivityObserver: ConnectivityObserver
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {
    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnMovieClicked -> onMovieClicked(event.movieId)


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

    init {
        loadGenres()
        observeConnectivity()
        updateState { copy(movies = buildMoviesFlow()) }
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { connected ->
                updateState { copy(isConnected = connected) }
            }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { result ->
                when (result) {
                    is ApiResult.Success -> updateState {
                        copy(
                            genres = result.data,
                            genresLoaded = true
                        )
                    }

                    is ApiResult.Loading -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }

                    is ApiResult.Error -> {
                        updateState {
                            copy(
                                isLoading = false,
                                errorMessage = getErrorStrings(result.networkError),
                                genresLoaded = true
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun buildMoviesFlow(): Flow<PagingData<MovieCardUiModel>> =
        state.distinctUntilChanged { old, new ->
            old.searchQuery == new.searchQuery && old.selectedGenreId == new.selectedGenreId
                    && old.genresLoaded == new.genresLoaded
        }.debounce(300.milliseconds).flatMapLatest { currentState ->
            if (!currentState.genresLoaded) {
                flowOf(PagingData.empty())
            } else {
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
                }.cachedIn(viewModelScope)
            }
        }.map { pagingData ->
            pagingData.map { movieResponse ->
                movieUiMapper.mapToUiModel(movieResponse)
            }
        }.combine(getAllFavouritesUseCase()) { pagingData, favouritesEntity ->
            val favouriteIds = favouritesEntity.map { it.movieId }.toSet()
            pagingData.map { movieCardUiModel ->
                movieCardUiModel.copy(isFavourite = favouriteIds.contains(movieCardUiModel.id))
            }
        }

    private fun onMovieClicked(movieId: Int) {
        emitSideEffect(
            HomeSideEffect.Navigate(
                NavigationCommand.Push(MovieDetailsRoute(movieId))
            )
        )
    }
}