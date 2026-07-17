package com.space.presentaton.contract

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
)