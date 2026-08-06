package com.space.data.remote.datasource.implementation

import com.space.data.remote.api.MovieDetailsApi
import com.space.data.remote.datasource.contract.MovieDetailsRemoteDataSource
import com.space.data.remote.dto.MovieDetailsDto
import retrofit2.Response

class MovieDetailsRemoteDataSourceImpl(
    private val movieDetailsApi: MovieDetailsApi
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsDto> {
        return movieDetailsApi.getMovieDetails(movieId)
    }
}