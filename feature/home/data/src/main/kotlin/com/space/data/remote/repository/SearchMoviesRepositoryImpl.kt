package com.space.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.space.data.remote.datasource.contract.SearchRemoteDataSource
import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.paging.SearchPagingSource
import com.space.domain.model.MovieResponse
import com.space.domain.repository.SearchMoviesRepository
import com.space.networking.network.ResponseHandler
import kotlinx.coroutines.flow.Flow

class SearchMoviesRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val responseHandler: ResponseHandler,
    private val pagingConfig: PagingConfig
) : SearchMoviesRepository {

    override fun searchMovies(query: String): Flow<PagingData<MovieResponse>> {
        return Pager(
            pagingConfig,
            pagingSourceFactory = {
                SearchPagingSource(
                    searchRemoteDataSource,
                    query = query,
                    movieMapper = movieMapper,
                    responseHandler
                )
            }
        ).flow
    }
}