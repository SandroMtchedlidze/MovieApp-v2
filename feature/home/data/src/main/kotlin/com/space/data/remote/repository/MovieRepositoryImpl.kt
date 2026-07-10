package com.space.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.space.data.remote.cache.GenreCache
import com.space.data.remote.datasource.contract.MovieRemoteDataSource
import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.paging.MoviePagingSource
import com.space.domain.model.MovieResponse
import com.space.domain.repository.MovieRepository
import com.space.networking.network.ResponseHandler
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val responseHandler: ResponseHandler,
    private val pagingConfig: PagingConfig,
    private val genreCache: GenreCache
) : MovieRepository {

    override fun getMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            pagingConfig,
            pagingSourceFactory = {
                MoviePagingSource(
                    remoteDataSource,
                    genreCache.get(),
                    movieMapper,
                    responseHandler
                )
            }
        ).flow
    }
}