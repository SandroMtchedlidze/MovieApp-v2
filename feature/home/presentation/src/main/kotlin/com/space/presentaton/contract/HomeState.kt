package com.space.presentaton.contract

import androidx.paging.PagingData
import com.space.ui.component.MovieCardUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val genres: Map<Int, String> = emptyMap(),
    val isFilterVisible: Boolean = false,
    val selectedGenreId: Int? = null,
    val isSearchFocused: Boolean = false,
    val movies: Flow<PagingData<MovieCardUiModel>> = emptyFlow()
)