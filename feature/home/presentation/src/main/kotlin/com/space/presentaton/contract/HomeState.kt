package com.space.presentaton.contract

import androidx.annotation.StringRes

data class HomeState(
    val isLoading: Boolean = false,
    @param:StringRes
    val errorMessage: Int? = null,
    val searchQuery: String = "",
    val genres: Map<Int, String> = emptyMap(),
    val isFilterVisible: Boolean = false,
    val selectedGenreId: Int? = null,
    val isSearchFocused: Boolean = false,
    val hasInternetConnection: Boolean = true,
)