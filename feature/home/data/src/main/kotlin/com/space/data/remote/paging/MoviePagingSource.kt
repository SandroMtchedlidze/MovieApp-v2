package com.space.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.space.data.remote.datasource.contract.MovieRemoteDataSource
import com.space.data.remote.mapper.MovieMapper
import com.space.domain.model.MovieResponse
import com.space.networking.network.PagingException
import com.space.networking.network.PagingResult
import com.space.networking.network.ResponseHandler

class MoviePagingSource(
    private val remoteDataSource: MovieRemoteDataSource,
    private val genreCache: Map<Int, String>,
    private val movieMapper: MovieMapper,
    private val responseHandler: ResponseHandler
) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val page = params.key ?: 1
        return when (val result =
            responseHandler.pagingApiCall { remoteDataSource.getMovies(page) }) {
            is PagingResult.Success -> LoadResult.Page(
                data = result.data.results.map { dto ->
                    movieMapper.mapToDomain(dto, genreCache)
                },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.data.results.isEmpty()) null else page + 1
            )

            is PagingResult.Error ->
                LoadResult.Error(PagingException(result.error, result.message))
        }
    }
}