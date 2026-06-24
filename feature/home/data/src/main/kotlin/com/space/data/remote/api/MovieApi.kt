package com.space.data.remote.api

import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponseDto>
}