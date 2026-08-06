package com.space.movieapp.ui.contract

import androidx.navigation3.runtime.NavKey
import com.space.api.navigation.HomeRoute

data class MainActivityState(
    val isLoading: Boolean = true,
    val isOnline: Boolean = false,
    val startDestination: NavKey = HomeRoute
)