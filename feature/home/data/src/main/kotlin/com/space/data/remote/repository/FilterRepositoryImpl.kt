package com.space.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.space.data.remote.datasource.contract.DiscoverRemoteDataSource
import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.paging.DiscoverPagingSource
import com.space.domain.model.MovieResponse
import com.space.domain.repository.FilterRepository
import com.space.networking.network.ResponseHandler
import kotlinx.coroutines.flow.Flow

class FilterRepositoryImpl(
    private val discoverRemoteDataSource: DiscoverRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val responseHandler: ResponseHandler,
    private val pagingConfig: PagingConfig
) : FilterRepository {

    override fun discoverMoviesByGenre(genreId: Int): Flow<PagingData<MovieResponse>> {
        return Pager(
            pagingConfig,
            pagingSourceFactory = {
                DiscoverPagingSource(
                    discoverRemoteDataSource,
                    movieMapper,
                    genreId,
                    responseHandler
                )
            }
        ).flow
    }
}