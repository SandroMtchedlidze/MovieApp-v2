package com.space.presentation.vm

import androidx.lifecycle.viewModelScope
import com.space.domain.usecase.GetMovieDetailsUseCase
import com.space.domain.usecase.IsFavouriteUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import com.space.networking.network.ApiResult
import com.space.presentation.base.BaseViewModel
import com.space.presentation.contract.MovieDetailsEvent
import com.space.presentation.contract.MovieDetailsSideEffect
import com.space.presentation.contract.MovieDetailsState
import com.space.presentation.mapper.MovieDetailsToDomain
import com.space.presentation.mapper.MovieDetailsUiMapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MovieDetailsVm(
    private val movieId: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val uiMapper: MovieDetailsUiMapper,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val isFavouriteUseCase: IsFavouriteUseCase,
    private val domainMapper: MovieDetailsToDomain,
) : BaseViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsSideEffect>(
    MovieDetailsState()
) {
    override fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.OnRetryClicked -> fetchMovieDetails()
            is MovieDetailsEvent.OnBackClicked ->
                emitSideEffect(MovieDetailsSideEffect.NavigateToBack)

            is MovieDetailsEvent.OnFavouriteClicked -> {
                handleFavouriteClicked(event)
            }
        }
    }

    init {
        fetchMovieDetails()
        observeFavouriteState()
    }

    private fun observeFavouriteState() {
        isFavouriteUseCase(movieId).onEach { isFav ->
            updateState { copy(isFavourite = isFav) }
        }.launchIn(viewModelScope)
    }

    private fun handleFavouriteClicked(event: MovieDetailsEvent.OnFavouriteClicked) {
        viewModelScope.launch {
            val domainMovie = domainMapper.uiModelToDomain(event.movieDetailsUiModel)
            toggleFavouriteUseCase(domainMovie)
        }
    }

    private fun fetchMovieDetails() {
        getMovieDetailsUseCase(movieId).onEach { result ->
            when (result) {
                is ApiResult.Loading -> updateState {
                    copy(
                        isLoading = result.isLoading,
                        errorMessage = null
                    )
                }

                is ApiResult.Error -> updateState {
                    copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }

                is ApiResult.Success -> updateState {
                    copy(
                        movie = uiMapper.mapToUi(result.data),
                        errorMessage = null
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}