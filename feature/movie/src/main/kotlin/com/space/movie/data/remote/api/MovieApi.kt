package com.space.movie.data.remote.api

import com.space.movie.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponseDto>
}