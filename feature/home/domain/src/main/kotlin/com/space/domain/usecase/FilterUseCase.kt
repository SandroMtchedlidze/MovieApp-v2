package com.space.domain.usecase

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import com.space.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

class FilterUseCase(
    private val repository: FilterRepository
) {
    operator fun invoke(genreId: Int): Flow<PagingData<MovieResponse>> {
        return repository.discoverMoviesByGenre(genreId)
    }
}