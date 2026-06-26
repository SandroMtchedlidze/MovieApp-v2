package com.space.domain.usecase

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import com.space.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(query: String): Flow<PagingData<MovieResponse>> {
        return repository.searchMovies(query)
    }
}