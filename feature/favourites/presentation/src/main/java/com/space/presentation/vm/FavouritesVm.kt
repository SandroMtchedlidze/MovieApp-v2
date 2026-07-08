package com.space.presentation.vm

import androidx.lifecycle.viewModelScope
import com.space.domain.usecase.GetAllFavouritesUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import com.space.presentation.base.BaseViewModel
import com.space.presentation.contract.FavouritesEffect
import com.space.presentation.contract.FavouritesEvent
import com.space.presentation.contract.FavouritesState
import com.space.presentation.mapper.MovieUiModelToDomain
import com.space.ui.component.MovieCardUiModel
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
        viewModelScope.launch {
            getAllFavouritesUseCase().collect { favourites ->
                updateState {
                    copy(
                        isLoading = false,
                        favourites = favourites.map { mapper.toUiModel(it) }
                    )
                }
            }
        }
    }

    private fun toggleFavourites(movie: MovieCardUiModel) {
        viewModelScope.launch {
            toggleFavouriteUseCase(mapper.toDomain(movie))
        }
    }
}
