package com.space.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.space.data.remote.api.DiscoverApi
import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.paging.DiscoverPagingSource
import com.space.domain.model.MovieResponse
import com.space.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

class FilterRepositoryImpl(
    private val discoverApi: DiscoverApi,
    private val movieMapper: MovieMapper
) : FilterRepository {

    override fun discoverMoviesByGenre(genreId: Int): Flow<PagingData<MovieResponse>> {
        return Pager(
            defaultPagingConfig,
            pagingSourceFactory = {
                DiscoverPagingSource(
                    discoverApi,
                    movieMapper,
                    genreId,
                    genreCache
                )
            }
        ).flow
    }
}