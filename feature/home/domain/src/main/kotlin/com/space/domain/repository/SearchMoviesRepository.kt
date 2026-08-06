package com.space.domain.repository

import androidx.paging.PagingData
import com.space.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface SearchMoviesRepository {
    fun searchMovies(query: String): Flow<PagingData<MovieResponse>>
}