package com.space.data.remote.datasource.contract

import com.space.data.remote.dto.movie.MovieResponseDto
import retrofit2.Response

interface DiscoverRemoteDataSource {
    suspend fun discoverMovies(genreId: Int, page: Int): Response<MovieResponseDto>
}