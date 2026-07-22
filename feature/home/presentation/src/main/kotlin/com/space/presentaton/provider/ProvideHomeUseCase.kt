package com.space.presentaton.provider

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import com.space.domain.usecase.FilterUseCase
import com.space.domain.usecase.GetPopularMoviesUseCase
import com.space.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.flow.Flow

class ProvideHomeUseCase(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val filterUseCase: FilterUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) {
    operator fun invoke(searchQuery: String, genreId: Int?): Flow<PagingData<MovieResponse>> =
        when {
            searchQuery.isNotEmpty() -> searchMoviesUseCase(searchQuery)
            genreId != null -> filterUseCase(genreId)
            else -> getPopularMoviesUseCase()
        }
}