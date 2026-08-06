package com.space.data.remote.datasource.contract

import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response


interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int): Response<MovieResponseDto>
}