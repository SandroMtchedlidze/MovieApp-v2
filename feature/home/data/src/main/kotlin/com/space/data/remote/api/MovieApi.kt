package com.space.data.remote.api

import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int
    ): Response<MovieResponseDto>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieResponseDto>
}