package com.space.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.space.data.remote.api.MovieApi
import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.paging.MoviePagingSource
import com.space.domain.model.MovieResponse
import com.space.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

val defaultPagingConfig = PagingConfig(
    pageSize = 20,
    prefetchDistance = 5,
    enablePlaceholders = false
)

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val movieMapper: MovieMapper,
) : MovieRepository {


    override fun getMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            defaultPagingConfig,
            pagingSourceFactory = {
                MoviePagingSource(
                    movieApi,
                    genreCache,
                    movieMapper
                )
            }
        ).flow
    }
}