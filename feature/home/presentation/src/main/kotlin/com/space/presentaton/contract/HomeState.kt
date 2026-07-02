package com.space.presentaton.contract

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val genres: Map<Int, String> = emptyMap(),
    val isFilterVisible: Boolean = false,
    val selectedGenreId: Int? = null,
    val isSearchFocused: Boolean = false
)