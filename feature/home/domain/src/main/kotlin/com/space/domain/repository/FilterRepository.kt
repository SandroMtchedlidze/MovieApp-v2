package com.space.domain.repository

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun discoverMoviesByGenre(genreId: Int): Flow<PagingData<MovieResponse>>
}