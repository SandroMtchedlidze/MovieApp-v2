package com.space.data.remote.datasource.contract

import com.space.data.remote.dto.MovieDetailsDto
import retrofit2.Response


interface MovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsDto>
}