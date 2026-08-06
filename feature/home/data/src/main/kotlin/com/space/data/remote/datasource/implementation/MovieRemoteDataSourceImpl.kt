package com.space.data.remote.datasource.implementation

import com.space.data.remote.api.MovieApi
import com.space.data.remote.datasource.contract.MovieRemoteDataSource
import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val movieApi: MovieApi
) : MovieRemoteDataSource {
    override suspend fun getMovies(page: Int): Response<MovieResponseDto> {
        return movieApi.getMovies(page)
    }
}