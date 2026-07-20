package com.space.domain.usecase

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow


class GetMoviesUseCase(
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