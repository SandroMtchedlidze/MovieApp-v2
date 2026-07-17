package com.space.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.space.data.remote.api.MovieApi
import com.space.data.remote.mapper.MovieMapper
import com.space.domain.model.MovieResponse

class SearchPagingSource(
    private val movieApi: MovieApi,
    private val query: String,
    private val genreCache: Map<Int, String>,
    private val movieMapper: MovieMapper
) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val page = params.key ?: 1
        return try {
            val response = movieApi.searchMovies(
                query = query,
                page = page
            )

            val movies = response.body()?.results
                ?.map { movieMapper.mapToDomain(it, genreCache) } ?: emptyList()
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}