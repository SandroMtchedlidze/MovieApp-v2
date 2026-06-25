package com.space.presentaton.contract

import androidx.paging.PagingData
import com.space.ui.component.MovieCardUiModel

data class HomeState(
 //   val movies: PagingData<MovieCardUiModel> = PagingData.empty(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)