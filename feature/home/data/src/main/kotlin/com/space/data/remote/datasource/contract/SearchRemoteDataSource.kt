package com.space.data.remote.datasource.contract

import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response

interface SearchRemoteDataSource {
    suspend fun searchMovies(query: String, page: Int): Response<MovieResponseDto>
}