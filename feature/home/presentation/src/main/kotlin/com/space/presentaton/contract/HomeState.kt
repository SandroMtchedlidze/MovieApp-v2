package com.space.presentaton.contract

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.space.ui.component.MovieCardUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val isLoading: Boolean = false,
    @param:StringRes
    val errorMessage: Int? = null,
    val searchQuery: String = "",
    val genres: Map<Int, String> = emptyMap(),
    val isFilterVisible: Boolean = false,
    val genresLoaded: Boolean = false,
    val selectedGenreId: Int? = null,
    val isSearchFocused: Boolean = false,
    val isConnected: Boolean = true,
    val movies: Flow<PagingData<MovieCardUiModel>> = emptyFlow()
)