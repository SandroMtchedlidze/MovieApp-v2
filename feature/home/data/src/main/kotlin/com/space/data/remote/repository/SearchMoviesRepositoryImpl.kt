package com.space.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.space.data.remote.api.SearchApi
import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.paging.SearchPagingSource
import com.space.domain.model.MovieResponse
import com.space.domain.repository.SearchMoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesRepositoryImpl(
    private val searchApi: SearchApi,
    private val movieMapper: MovieMapper
) : SearchMoviesRepository {

    override fun searchMovies(query: String): Flow<PagingData<MovieResponse>> {
        return Pager(
            defaultPagingConfig,
            pagingSourceFactory = {
                SearchPagingSource(
                    searchApi,
                    query = query,
                    genreCache = genreCache,
                    movieMapper = movieMapper
                )
            }
        ).flow
    }
}