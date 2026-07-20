package com.space.domain.usecase

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow


class HomeMoviesUseCase(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val filterUseCase: FilterUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) {
    operator fun invoke(searchQuery: String, genreId: Int?): Flow<PagingData<MovieResponse>> =
        when {
            searchQuery.isNotEmpty() -> searchMoviesUseCase(searchQuery)
            genreId != null -> filterUseCase(genreId)
            else -> getMoviesUseCase()
        }
}