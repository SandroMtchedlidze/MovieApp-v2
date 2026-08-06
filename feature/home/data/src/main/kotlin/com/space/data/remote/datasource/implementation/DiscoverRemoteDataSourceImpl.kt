package com.space.data.remote.datasource.implementation

import com.space.data.remote.api.DiscoverApi
import com.space.data.remote.datasource.contract.DiscoverRemoteDataSource
import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response

class DiscoverRemoteDataSourceImpl(
    private val discoverApi: DiscoverApi
) : DiscoverRemoteDataSource {
    override suspend fun discoverMovies(
        genreId: Int,
        page: Int
    ): Response<MovieResponseDto> {
        return discoverApi.discoverMovies(genreId, page)
    }
}