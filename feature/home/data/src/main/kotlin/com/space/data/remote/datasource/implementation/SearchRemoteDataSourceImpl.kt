package com.space.data.remote.datasource.implementation

import com.space.data.remote.api.SearchApi
import com.space.data.remote.datasource.contract.SearchRemoteDataSource
import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response

class SearchRemoteDataSourceImpl(
    private val searchApi: SearchApi
) : SearchRemoteDataSource {
    override suspend fun searchMovies(query: String, page: Int): Response<MovieResponseDto> {
        return searchApi.searchMovies(
            query,
            page
        )
    }
}