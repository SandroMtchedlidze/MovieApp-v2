package com.space.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.space.data.remote.api.GenreApi
import com.space.data.remote.api.MovieApi
import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.paging.MoviePagingSource
import com.space.data.remote.paging.SearchPagingSource
import com.space.domain.model.MovieResponse
import com.space.domain.repository.MovieRepository
import com.space.networking.network.ApiResult
import com.space.networking.network.apiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val genreApi: GenreApi,
    private val movieMapper: MovieMapper
) : MovieRepository {

    private val defaultPagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        enablePlaceholders = false
    )

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

    private var genreCache: Map<Int, String> = emptyMap()

    override fun getGenres(): Flow<ApiResult<Map<Int, String>>> = flow {
        if (genreCache.isNotEmpty()) {
            emit(ApiResult.Loading(isLoading = false))
            emit(ApiResult.Success(genreCache))
            return@flow
        }
        apiCall { genreApi.getGenres() }.collect { result ->
            when (result) {
                is ApiResult.Loading -> emit(result)
                is ApiResult.Success -> {
                    genreCache = result.data.genres.associate { it.id to it.name }
                    emit(ApiResult.Success(genreCache))
                }

                is ApiResult.Error -> emit(result)
            }
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<MovieResponse>> {
        return Pager(
            defaultPagingConfig,
            pagingSourceFactory = {
                SearchPagingSource(
                    movieApi,
                    query = query,
                    genreCache = genreCache,
                    movieMapper = movieMapper
                )
            }
        ).flow
    }
}