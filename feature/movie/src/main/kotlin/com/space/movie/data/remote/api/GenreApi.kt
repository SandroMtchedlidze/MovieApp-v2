package com.space.movie.data.remote.api

import com.space.movie.data.remote.dto.genre.GenreResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface GenreApi {

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreResponseDto>
}