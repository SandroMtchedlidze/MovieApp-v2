package com.space.presentation.vm

import androidx.lifecycle.viewModelScope
import com.space.domain.usecase.GetAllFavouritesUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import com.space.networking.network.NetworkError
import com.space.presentation.base.BaseViewModel
import com.space.presentation.contract.FavouritesEffect
import com.space.presentation.contract.FavouritesEvent
import com.space.presentation.contract.FavouritesState
import com.space.presentation.mapper.MovieUiModelToDomain
import com.space.ui.component.MovieCardUiModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavouritesVm(
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val mapper: MovieUiModelToDomain
) : BaseViewModel<FavouritesState, FavouritesEvent, FavouritesEffect>(
    FavouritesState()
) {
    override fun onEvent(event: FavouritesEvent) {
        when (event) {
            is FavouritesEvent.LoadFavourites -> observeFavourites()
            is FavouritesEvent.OnFavouriteToggle -> toggleFavourites(event.movie)
            is FavouritesEvent.OnMovieClicked -> emitSideEffect(
                FavouritesEffect.NavigateToDetails(
                    event.movieId
                )
            )
        }
    }

    init {
        onEvent(FavouritesEvent.LoadFavourites)
    }

    private fun observeFavourites() {
        getAllFavouritesUseCase()
            .onStart { updateState { copy(isLoading = true) } }
            .map { responses -> responses.map { mapper.toUiModel(it) } }
            .onEach { uiModels ->
                updateState {
                    copy(
                        favourites = uiModels,
                        isLoading = false,
                        error = null
                    )
                }
            }.catch {
                updateState { copy(isLoading = false, error = NetworkError.UNKNOWN.name) }
            }
            .launchIn(viewModelScope)
    }

    private fun toggleFavourites(movie: MovieCardUiModel) {
        viewModelScope.launch {
            toggleFavouriteUseCase(mapper.toDomain(movie))
        }
    }
}